import java.util.Comparator;

public class TwinSortedList {
	
  public Container first;
  public Container last;
  private Comparator comp;
  private int size;
  private TwinSortedList twinList;
  
  public TwinSortedList(Comparator comp){
	  this.first=null;
	  this.last=null;
	  this.comp=comp;
	  this.size=0;
  }

  public void SetTwin(TwinSortedList twinList){
	  this.twinList=twinList;
  }
  
  
  /*
   * ************ the two closestFrom methods ************
   * get methods, that allow quicker access to items in the list, 
   * given the information about the sided the item is closest to.
   */
  
  public Container closestFromStart(Point val){
	  Container startPointer =this.first;
	  while(comp.compare(val, startPointer.getData())==1){
		  startPointer=startPointer.getNext();
	  }
	  return startPointer;
  }
  
  public Container closestFromEnd(Point val){
	  Container endPointer=this.last;
	  while(comp.compare(val, endPointer.getData())==-1){
		  endPointer=endPointer.getBack();
	  }
	  return endPointer;
  }
  
  /*******************************END of closestFrom methods******************************/

  
  /*
   * ************* add method ************
   * a method that inserts items to the list
   * and keeping it sorted, according to a partial order that
   * the comparator is inflicting.
   * the method returns the node which the data was inserted into, so the addPoint method
   * in DataStracture could use it.
   * the method also update the size of the list
   */
  
  public Container add(Point data){
	Container toReturn =null; 
	 if(this.first==null){
		 this.first=new Container(data);
		 this.last=first;
		 toReturn=this.first;
	 }
	 else{
		 if(comp.compare(data,this.first.getData())==-1){
			Container  newLink= new Container(data);
			 newLink.setNext(first);
			 this.first.setBack(newLink);
			 this.first=newLink;
			 toReturn= newLink;
		 }
		 else if(comp.compare(data, this.last.getData())==1){
			Container  newLink= new Container(data);
			 newLink.setBack(this.last);
			 this.last.setNext(newLink);
			 this.last=newLink;
			 toReturn=newLink;
		 }
		 else{
			 Container temp = first;
			 while(temp!=null && comp.compare(data,temp.getData())==1){
				 temp=temp.getNext();
			 }
			Container  newLink= new Container(data);
			Container back=temp.getBack();
			 back.setNext(newLink);
			 newLink.setBack(back);
			 newLink.setNext(temp);
			 temp.setBack(newLink);
			 toReturn=newLink;
			 }
		 }
		 
	 
	 this.size++;
	 return toReturn;
  }
  
  /*******************************END of add methods******************************/

  
 /*
   * *************delete method**********************
   * using the fact that we are working on a doubly linked list
   * we will reach the segment from both ends and change the first and last 
   * pointers of the list, making it as if the other elements(not in the segment)
   * were never in the list.
   * mean while we will remove each item individually from the twin list, using 
   * the pointer "twin"
   * note: this method can be used just inside DataStracture, if this list
   * is independent, this method will course an exception
 */
  
  public void delete(Point max, Point min){
	  int counter=0; //counter for counting how many items are we deleting
	 Container  startPointer =this.first;
	  while(comp.compare(min, startPointer.getData())==1){
		  this.deleteInTwinList(startPointer);
		  startPointer=startPointer.getNext();
		  counter++;
	  }
	 Container  endPointer=this.last;
	  while(comp.compare(max, endPointer.getData())==-1){
		  this.deleteInTwinList(endPointer);
		  endPointer=endPointer.getBack();
		  counter++;
	  }
	  startPointer.setBack(null);
	  this.first=startPointer;
	  endPointer.setNext(null);
	  this.last=endPointer;
	  this.size-=counter;
	  this.twinList.size-=counter;
  }
  
  /*
   * *************** Assisting method****************
   * simple algoritem that reach the twin list and remove the data of "otherNode"
   * from it.
   */
  private void deleteInTwinList(Container otherNode){
	 Container  other = otherNode.getTwin();
	  if(other.getBack()!=null & other.getNext()!=null){ //Inner part of the list
		 Container  back=other.getBack();
		 Container  next= other.getNext();
		  back.setNext(next);
		  next.setBack(back);
	  }
	  else if(other.getBack()==null){ //other.back==null => at the beginning of the list
		  this.twinList.first=other.getNext();
		  this.twinList.first.setBack(null);
	  }
	  else{//other.next==null => at the end of list
		  this.twinList.last=other.getBack();
		  this.twinList.last.setNext(null);
	  }
  }
/*******************************END of delete method******************************/

  /*
   * *************** Assisting  method addLast****************
   * simple algorithm that allow the user to insert a new container at the end of the list, but those not guarantee keeping
   * partial order on the list
   */
  
  public Container addLast(Container data){
	  Container toReturn=null;
	 if(this.first==null){
		 this.first=new Container(data.getData());
		 this.last=first;
	     this.first.setTwin(data.getTwin());
	     this.first.marker=data.marker;
	     toReturn=this.first;
	 }
	 else{
		 Container newLink=new Container(data.getData());
		 newLink.marker=data.marker;
		 newLink.setBack(this.last);
		 newLink.setTwin(data.getTwin());
		 this.last.setNext(newLink);
		 this.last=newLink;
		 toReturn=newLink;
	 }
	 this.size++;
	 return toReturn;
  }
  
  public int getSize(){
	  return this.size;
  }
  
  public void setSize(int size){
	  this.size=size;
  }
  
  /*
   * *************** toString()****************
   * return a string the depicts the list
   */
  
  @Override
  public String toString() {
	String s ="SortedList: [ ";
	Container temp= this.first;
	while (temp.getData().getX()!=this.last.getData().getX() & temp.getData().getY()!=this.last.getData().getY()){
		s+=temp.getData().toString()+" , ";
		temp=temp.getNext();
	}
	s+=temp.getData().toString()+" , ";
	s=s.substring(0,s.length()-2);
	return s+"]";
  }
  
 
  
  
}
