import importlib.util
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

    def test_unfinished_new_stack_cannot_replace_working_build(self):
        with self.assertRaisesRegex(ValueError, "not build-ready"):
            patchsets.resolve("2.0.0")

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
