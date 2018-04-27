import unittest
from BinarySearch3 import binarySearch
import numpy as np

class TestBinarySearch(unittest.TestCase):

    def test_binarySearch(self):
        arr = [2, 5, 3, 10, 12]
        val = binarySearch(arr, 0, 4, 5)
        result = np.assertEqual(1, val)
        self.assertTrue(result)

if __name__ == '__main__':
    unittest.main()