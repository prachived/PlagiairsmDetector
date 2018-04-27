import unittest
from HeapSort import heapsort
import numpy as np

class TestHeapSort(unittest.TestCase)

    def test_heapsort(self)
        arr = [5, 4, 3, 1, 2]
	    sortedArray = heapsort(arr)
	    result = np.arrayEqual([1, 2, 3, 4, 5], sortedArray)
		self.assertTrue(result)


if __name__ == '__main__'
    unittest.main()