import java.util.*;
class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        if (m > n) {
            int[] temp = nums1;
            nums1 = nums2;
            nums2 = temp;
            int tmp = m;
            m = n;
            n = tmp;
        }
        int left = 0, right = m;
        while (left <= right) {
            int i = (left + right) / 2;
            int j = (m + n + 1) / 2 - i;
            if (i < m && nums2[j-1] > nums1[i]) {
                left = i + 1;
            } else if (i > 0 && nums1[i-1] > nums2[j]) {
                right = i - 1;
            } else {
                int maxLeft = 0;
                if (i == 0) maxLeft = nums2[j-1];
                else if (j == 0) maxLeft = nums1[i-1];
                else maxLeft = Math.max(nums1[i-1], nums2[j-1]);
                if ((m + n) % 2 == 1) return (double)maxLeft;
                
                int minRight = 0;
                if (i == m) minRight = nums2[j];
                else if (j == n) minRight = nums1[i];
                else minRight = Math.min(nums1[i], nums2[j]);
                
                return ((double)(maxLeft + minRight) / 2.0);
            }
        }
        return 0.0;
    }
	public static void main(String[] args) {
		int[] nums1 =  {1,2};
		int[] nums2 = {3,4};
		System.out.println(new Solution().findMedianSortedArrays(nums1 ,nums2));
	}
}