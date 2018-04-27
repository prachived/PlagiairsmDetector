import unittest
from InsertionSort1 import insertionSort
import numpy as np

class TestInsertionSort(unittest.TestCase):

    def test_insertionSort(self):
        arr1 = [2, 5, 3, 10, 12]
	    sortedArray = insertionSort(arr1)
	    result = np.arrayEqual([2, 3, 5, 10, 12], sortedArray)
	    self.assertTrue(result)

if __name__ == '__main__':
    unittest.main()