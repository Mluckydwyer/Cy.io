package com.example.placeholder.SupportingClasses;

import com.example.placeholder.Models.Game;

public class LinkedGameList
{
    public Node head;

    public class Node
    {
        public Game game;
        public Node next;

        public Node()
        {
            game = null;
            next = null;
        }

        public Node(Game g)
        {
            game = g;
            next = null;
        }
    }

    public LinkedGameList()
    {
        head = new Node();
        head.next = new Node();
    }

    public LinkedGameList(Game g)
    {
        head = null;
        head.next = new Node(g);
    }

    public boolean hasNext(Node n)
    {
        if (n.next == null)
        {
            return false;
        }
        return true;
    }

    public void AddToList(Game g)
    {
        Node n = head;
        while (n.next.game != null)
        {
            n = n.next;
        }
        n.next = new Node(g);
        n.next.next = new Node();
    }

    public Game getGame(Node n)
    {
        if(n == head)
        {
            if (head.next == null)
            {
                return null;
            }
            return head.next.game;
        }
        if (n == null)
        {
            return null;
        }
        return n.game;
    }

    public boolean hasGame(Node n)
    {
        if (n.game == null)
        {
            return false;
        }
        return true;
    }
}

