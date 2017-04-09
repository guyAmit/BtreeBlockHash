import java.util.Comparator;

public class TwinSortedList<T> {
	
  public ThreeSidedNode first;
  public ThreeSidedNode last;
  private Comparator<T> comp;
  private int size;
  private TwinSortedList<T> twinList;
  
  public TwinSortedList(Comparator<T> comp){
	  this.first=null;
	  this.last=null;
	  this.comp=comp;
	  this.size=0;
  }
  
  public void SetTwin(TwinSortedList<T> twinList){
	  this.twinList=twinList;
  }
  
  //add methood
  public ThreeSidedNode add(T data){
	 ThreeSidedNode toReturn =null; 
	 if(this.first==null){
		 this.first=new ThreeSidedNode(data);
		 this.last=first;
		 toReturn=this.first;
	 }
	 else{
		 if(comp.compare(data, (T)this.first.getData())<1){
			 ThreeSidedNode newLink= new ThreeSidedNode(data);
			 newLink.setNext(first);
			 this.first.setBack(newLink);
			 this.first=newLink;
			 toReturn= this.first;
		 }
		 else{
			 ThreeSidedNode temp = first;
			 while(temp!=null && comp.compare((T)temp.getData(), data)<1){
				 temp=temp.getNext();
			 }
			 if(temp==null){
				 ThreeSidedNode newLink= new ThreeSidedNode(data);
				 this.last.setNext(newLink);
				 newLink.setBack(this.last);
				 this.last=newLink;
				 toReturn =this.last;
				}
			 else{
				 ThreeSidedNode prev = temp.getBack();
				 ThreeSidedNode newLink= new ThreeSidedNode(data);
				 prev.setNext(newLink);
				 newLink.setNext(temp);
				 toReturn=temp;
			 }
		 }
		 
	 }
	 this.size++;
	 return toReturn;
  }
  
  
  //assiting methood for delete
  private void deleteInOtherList(ThreeSidedNode otherNode){
	  ThreeSidedNode other = otherNode.getTwin();
	  if(other.getBack()!=null & other.getNext()!=null){
		  ThreeSidedNode back=other.getBack();
		  ThreeSidedNode next= other.getNext();
		  back.setNext(next);
		  next.setBack(back);
	  }
	  else if(other.getBack()==null){ //other.back==null => at the begining of the list
		  this.twinList.first=other.getNext();
	  }
	  else{//other.next==null => at ther end of list
		  this.twinList.last=other.getBack();
	  }
  }
  
  public void delete(T max, T min){
	  int counter=0; //counter for counting how many items are we deleting
	  ThreeSidedNode startPointer =this.first;
	  while(comp.compare(min, (T)startPointer.getData())>1){
		  this.deleteInOtherList(startPointer);
		  startPointer=startPointer.getNext();
		  counter++;
	  }
	  ThreeSidedNode endPointer=this.last;
	  while(comp.compare(max, (T)endPointer.getData())<1){
		  this.deleteInOtherList(endPointer);
		  endPointer=endPointer.getBack();
		  counter++;
	  }
	  this.first=startPointer;
	  this.last=endPointer;
	  this.size-=counter;
	  this.twinList.size-=counter;
  }
  
  

  
  @Override
  public String toString() {
	String s ="SortedList: [ ";
	ThreeSidedNode temp= first;
	while (temp!=null){
		s+=temp.getData().toString()+" , ";
		temp=temp.getNext();
	}
	s=s.substring(0,s.length()-2);
	return s+"]";
  }
  
 
  
  
}
