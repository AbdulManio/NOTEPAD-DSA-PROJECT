package project;



class Node 
{
	String data;
	Node next , prev;

	Node(String data)
	{
		this.data = data;
		next = prev = null;
	}

}
public class Stack
{
	Node top  , tail;
	public void push(String data)
	{
		Node newNode = new Node(data);
		if(top == null)
		{
			top = newNode;
			tail = newNode;
		}
		else{
			tail.next = newNode;
			newNode.prev = tail;
			tail = newNode;
			
		}
	}

	public String redoPop()
	{
		if(tail != null)
		{
			if(tail.next != null)
			{
				String text = tail.next.data;
				tail = tail.next;
				return text;
			}
			else
			{
				String text = tail.data;
				return text;
			}
		}
		else
			{
				String text = tail.data;
				return text;
			}
	}
	public String pop(){
		if(tail != null)
		{
			if(tail.prev != null)
			{
				String text = tail.prev.data;
				tail = tail.prev;
				return text;
			}
			else {
				
				String text = "";
				return text;	
			}
		}
		else
		{
				String text = "";
				return text;	
		}
	}
}
