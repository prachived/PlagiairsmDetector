def find_duplicate_bf(A):
    n = len(A)
    for i in range(n):
        for j in range(i+1,n):
            if A[i] == A[j]:
                return A[i]