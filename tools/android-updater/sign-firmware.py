"""Sign an X4 Pro firmware image for Wi-Fi installation."""

import argparse
from pathlib import Path
import subprocess

MAGIC = b"CRASHOTA1"


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("firmware", type=Path)
    parser.add_argument("output", type=Path)
    parser.add_argument("--key", type=Path,
                        default=Path(__file__).parent / ".signing" / "firmware.pem")
    args = parser.parse_args()
    image = args.firmware.read_bytes()
    if not 24 <= len(image) <= 6553600 or image[0] != 0xE9:
        parser.error("Expected a raw ESP32 firmware image that fits the update partition.")
    signature = subprocess.run(["openssl", "dgst", "-sha256", "-sign", str(args.key)],
                               input=image, capture_output=True, check=True).stdout
    if len(signature) != 256:
        parser.error("Expected a 2048-bit RSA signing key.")
    with args.output.open("xb") as output:
        output.write(image)
        output.write(signature)
        output.write(MAGIC)


if __name__ == "__main__":
    main()
