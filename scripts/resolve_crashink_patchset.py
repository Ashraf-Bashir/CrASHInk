#!/usr/bin/env python3
"""Resolve an explicitly versioned, verified-ready firmware baseline."""
import argparse
import hashlib
import json
from pathlib import Path
import re

ROOT = Path(__file__).resolve().parents[1]


def resolve(version, root=ROOT):
    manifest = json.loads((root / "patchsets.json").read_text())
    entry = manifest["versions"].get(version)
    if entry is None:
        raise ValueError(f"Unknown crASHink baseline: {version}")
    if entry["status"] != "ready":
        raise ValueError(f"CrossInk {version} port is not build-ready; use 1.6.1 for now")
    directory = entry["directory"]
    if not re.fullmatch(r"patches-[0-9]+(?:\.[0-9]+){2,3}", directory):
        raise ValueError("Invalid patch directory")
    for key in ("commit", "sdk_commit"):
        if not re.fullmatch(r"[0-9a-f]{40}", entry[key]):
            raise ValueError(f"Invalid pinned {key}")
    patch_dir = root / directory
    if not list(patch_dir.glob("*.patch")):
        raise ValueError(f"No patches in {directory}")
    checksums = patch_dir / "SHA256SUMS"
    if checksums.exists():
        for line in checksums.read_text().splitlines():
            expected, path = line.split("  ", 1)
            file = patch_dir / path
            if not file.resolve().is_relative_to(patch_dir.resolve()):
                raise ValueError("Invalid checksum path")
            if hashlib.sha256(file.read_bytes()).hexdigest() != expected:
                raise ValueError(f"Archived patch changed: {directory}/{path}")
    return {"version": entry["tag"], "commit": entry["commit"],
            "sdk-commit": entry["sdk_commit"], "patch-directory": directory,
            "repository": manifest["upstream"]}


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("version")
    parser.add_argument("--github-output", type=Path)
    args = parser.parse_args()
    try:
        values = resolve(args.version)
    except (ValueError, OSError) as error:
        parser.exit(1, f"{error}\n")
    if args.github_output:
        with args.github_output.open("a") as output:
            for key, value in values.items():
                output.write(f"{key}={value}\n")
    print(json.dumps(values, indent=2))


if __name__ == "__main__":
    main()
