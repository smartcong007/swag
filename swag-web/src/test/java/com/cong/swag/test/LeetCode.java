package com.cong.swag.test;

import java.util.HashSet;
import java.util.Set;

/**
 * @Description TODO
 * @Author zheng cong
 * @Date 2019-07-29
 */
public class LeetCode {

    public static class ListNode{
        int val;
        ListNode next;
        ListNode(int x) {val = x;}
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode head = null;
        ListNode last = null;
        int jinWei = 0;
        while (l1!=null&&l2!=null) {
            int tol = l1.val+l2.val+jinWei;
            jinWei = tol/10;
            ListNode node = new ListNode(tol%10);
            if (head==null) {head = node;}
            if (last!=null) {
                last.next = node;
            }
            last = last.next;
            l1 = l1.next;
            l2 = l2.next;
        }
        while (l1!=null) {
            if (jinWei == 0) {
                last.next = l1;
                break;
            }
            int tol = l1.val+jinWei;
            jinWei = tol/10;
            ListNode node = new ListNode(tol%10);
            last.next= node;
            last=last.next;
            l1=l1.next;
        }
        while (l2!=null) {
            if (jinWei == 0) {
                last.next = l2;
                break;
            }
            int tol = l2.val+jinWei;
            jinWei = tol/10;
            ListNode node = new ListNode(tol%10);
            last.next= node;
            last=last.next;
            l2=l2.next;
        }
        if (jinWei == 1) {
            ListNode node = new ListNode(1);
            last.next = node;
        }
        return head;
    }

    public static void main(String[] args) {
        String s = "abcabcbb";
        Set<Character> set = new HashSet<>(s.length());
        for (int i=0;i<s.length();i++) {
            set.add(s.charAt(i));
        }
        System.out.println(set.size());
    }

}
