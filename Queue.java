public class Queue{
	public Node head;
	public Node tail;
	public Node start;
	public Queue(){
	    head = null;
	    tail = null;
	}
	public void push(Node node){
	    Node tmp;
	    tmp = node;
	    
	    if(head == null){
	        head = node;
	        tail = node;
	    }
	    else{
	        tail.next = tmp;
	        tail = tmp;
	    }
	    
	}
	public void delPop(){
	    Node tmp = head;
	    head = head.next;
	    tmp.next = null;
	}
	public Node pop(){
	    if(head == null){
	        return null;
	    }
	    else{
	        Node tmp = head;
	        head = head.next;
	        tmp.next = null;
	        return tmp;
	    }
	}
	public Node[] getPath(Node tmp){
	    Node[] x = new Node[(int)tmp.level+1];
	    int i = 0;
	    while(tmp != start){
	        x[i] = tmp;
	        tmp = tmp.parent;
	        i++;
	        
	    }
	    return x;
	}
	public void print(){
	    Node tmp = head;
	    while(tmp!=null){
	        tmp = tmp.next;
	    }
	}
	public boolean isEmpty(){
	    return head == null;
	}
}