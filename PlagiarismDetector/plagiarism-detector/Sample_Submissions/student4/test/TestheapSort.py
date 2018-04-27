import unittest
from heapSort import heapSort
import numpy as np

class TestheapSort(unittest.TestCase):

    def test_heapSort(self):
        arr1 = [2, 5, 3, 10, 12]
	    sortedArray = heapSort(arr1)
	    result = np.arrayEqual([2, 3, 5, 10, 12], sortedArray)
	    self.assertTrue(result)

if __name__ == '__main__':
    unittest.main()