public class PriorityQueue extends Queue{
    @Override
    public void push(Node node){
        Node tmp;
        Node tmp2;
        if(head == null){
            head = node;
            tail = node;
        }
        else{
            tmp = head;
            tmp2 = tmp;
            
                try{
                    while(node.getFn() >= tmp.getFn() && tmp != null){
                    tmp2 = tmp;
                    tmp = tmp.next;
                    }
                }
                catch(Exception e){
                }
            if(tmp == head){
                node.next = head;
                head = node;
            }
            else if(tmp2 == tail){
                tail.next = node;
                tail = node;
                tail.next = null;
            }
            else{
                node.next = tmp;
                tmp2.next = node;
            }
        }
    }
}