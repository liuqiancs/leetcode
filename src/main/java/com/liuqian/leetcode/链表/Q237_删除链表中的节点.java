package com.liuqian.leetcode.链表;

/**
 * 参考:https://leetcode-cn.com/problems/delete-node-in-a-linked-list/solution/shan-chu-lian-biao-zhong-de-jie-dian-by-leetcode/
 */
public class Q237_删除链表中的节点 {
    public static void main(String[] args) {
        Solution s = new Q237_删除链表中的节点().new Solution();
        ListNode l4 = new ListNode(4);
        l4.next = null;
        ListNode l3 = new ListNode(3);
        l3.next = l4;
        ListNode l2 = new ListNode(2);
        l2.next = l3;
        ListNode l1 = new ListNode(1);
        l1.next = l2;
        s.deleteNode(l2);
        for(ListNode n = l1 ;n!=null ;n = n.next){
            System.out.println(n.val);
        }
    }


    class Solution {
        public void deleteNode(ListNode node) {
            node.val = node.next.val;
            node.next = node.next.next;
        }
    }
}
