import importlib.util
import json
from pathlib import Path
import shutil
import tempfile
import unittest

spec = importlib.util.spec_from_file_location("patchsets", Path(__file__).with_name("resolve_crashink_patchset.py"))
patchsets = importlib.util.module_from_spec(spec)
spec.loader.exec_module(patchsets)


class PatchsetsTest(unittest.TestCase):
    def test_old_baseline_is_pinned_without_remote_latest_lookup(self):
        selected = patchsets.resolve("1.6.1")
        self.assertEqual(selected["commit"], "9914146eeae7b46b300f475a16c32426fc02ec1f")
        self.assertEqual(selected["patch-directory"], "patches-1.6.1")
        self.assertEqual(selected["sdk-commit"], "699370183fa3a0e33c9cb83a36f701bbb6022095")

    def test_new_baseline_is_independent_and_pinned(self):
        selected = patchsets.resolve("2.0.0")
        self.assertEqual(selected["commit"], "2cdd6117568c8877c3180d5fac8d5b20e55d1ac7")
        self.assertEqual(selected["patch-directory"], "patches-2.0.0")
        self.assertEqual(selected["sdk-commit"], "f7fa9b6871192ea27d8aa37a03beaa749462c3ab")

    def test_unfinished_stack_cannot_replace_working_build(self):
        with tempfile.TemporaryDirectory() as folder:
            root = Path(folder)
            data = json.loads((patchsets.ROOT / "patchsets.json").read_text())
            data["versions"]["2.0.0"]["status"] = "porting"
            (root / "patchsets.json").write_text(json.dumps(data))
            with self.assertRaisesRegex(ValueError, "not build-ready"):
                patchsets.resolve("2.0.0", root)

    def test_unknown_baseline_is_rejected(self):
        with self.assertRaisesRegex(ValueError, "Unknown"):
            patchsets.resolve("../../patches")

    def test_archived_patch_changes_are_detected(self):
        with tempfile.TemporaryDirectory() as folder:
            root = Path(folder)
            shutil.copy(patchsets.ROOT / "patchsets.json", root)
            shutil.copytree(patchsets.ROOT / "patches-1.6.1", root / "patches-1.6.1")
            file = root / "patches-1.6.1/0005-clippings.patch"
            file.write_bytes(file.read_bytes() + b"\n# accidental modification\n")
            with self.assertRaisesRegex(ValueError, "Archived patch changed"):
                patchsets.resolve("1.6.1", root)


if __name__ == "__main__":
    unittest.main()
