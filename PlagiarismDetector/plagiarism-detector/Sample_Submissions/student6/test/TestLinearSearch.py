import unittest
from LinearSearch3 import linearSearch
import numpy as np

class TestLinearSearch(unittest.TestCase):

    def test_linearSearch(self):
        arr = [2, 5, 3, 10, 12]
        val = linearSearch(arr, 0, 4, 5)
        result = np.assertEqual(1, val)
        self.assertTrue(result)

if __name__ == '__main__':
    unittest.main()