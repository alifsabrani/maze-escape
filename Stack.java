public class Stack extends Queue{
	@Override
	public void push(Node node){
	    Node tmp;
	    tmp = node;
	    if(head == null){
	        head = node;
	        tail = node;
	    }
	    else{
	        tmp.next = head;
	        head = tmp;
	    }
	    
	}
}