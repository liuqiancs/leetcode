package com.liuqian.leetcode.链表;

/**
 * 参考：https://leetcode-cn.com/problems/remove-linked-list-elements/solution/yi-chu-lian-biao-yuan-su-by-leetcode/
 * https://leetcode-cn.com/problems/remove-linked-list-elements/solution/203yi-chu-lian-biao-yuan-su-by-lewis-dxstabdzew/
 * https://leetcode-cn.com/problems/remove-linked-list-elements/solution/dong-hua-yan-shi-203-yi-chu-lian-biao-yuan-su-by-u/
 */
public class Q203_移除链表元素 {

    public static void main(String[] args) {
//        Q203_移除链表元素.Solution s = new Q203_移除链表元素().new Solution();
//        Q203_移除链表元素.Solution1 s = new Q203_移除链表元素().new Solution1();
        Q203_移除链表元素.Solution3 s = new Q203_移除链表元素().new Solution3();

        ListNode l4 = new ListNode(4);
        l4.next = null;
        ListNode l3 = new ListNode(3);
        l3.next = l4;
        ListNode l2 = new ListNode(2);
        l2.next = l3;
        ListNode l1 = new ListNode(1);
        l1.next = l2;
        ListNode removeE = s.removeElements(l1,4);
        System.out.println(removeE.val);
    }

    //哨兵节点，伪头结点
    class Solution {
        public ListNode removeElements(ListNode head, int val) {
            ListNode sentinel = new ListNode(0);
            sentinel.next = head;
            ListNode prev = sentinel;
            for (ListNode curr = head; curr != null;curr = curr.next) {
                if (curr.val == val) prev.next = curr.next;
                else prev = curr;
            }
            return sentinel.next;
        }
    }

    //迭代
    class Solution1 {
        public ListNode removeElements(ListNode head, int val) {
            //删除值相同的头结点后，可能新的头结点也值相等，用循环解决
            while(head!=null&&head.val==val){
                head=head.next;
            }
            if(head==null)
                return head;
            ListNode preNode = head;
            for(ListNode currNode = head ; currNode != null ;currNode = currNode.next){
                if(currNode.val == val) {
                    preNode.next = currNode.next;
                }
                else {
                    preNode = currNode;
                }
            }
            return head;
        }
    }

    //迭代
    class Solution2 {
        public ListNode removeElements(ListNode head, int val) {
            ListNode current = head;
            ListNode prev = null;
            while(current != null) {
                if (current.val == val) {
                    if (prev == null) {
                        current = current.next;
                        head = current;
                    } else {
                        prev.next = current.next;
                        current = current.next;
                    }
                } else {
                    prev = current;
                    current = current.next;
                }
            }

            return head;
        }
    }


    //递归
    class Solution3 {
        public ListNode removeElements(ListNode head, int val) {
            if (head == null)
                return head;
            head.next = removeElements(head.next, val);
            return head.val == val ? head.next : head;
        }
    }

}
