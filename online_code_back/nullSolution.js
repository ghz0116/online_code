/**
 * @param {number[]} nums
 * @param {number} target
 * @return {number[]}
 */
var twoSum = function(nums, target) {
 const map = new Map();
    
    for (let i = 0; i < nums.length; i++) {
        const complement = target - nums[i];
        
        if (map.has(complement)) {
            return [map.get(complement), i];
        }
       
        map.set(nums[i], i);
    }
    
    return null; // 如果没有找到匹配的两个数，返回null或者其他指示值
};
console.log(JSON.stringify(twoSum([3,2,4] ,6)))