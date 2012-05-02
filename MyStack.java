/*THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING A TUTOR OR CODE WRITTEN BY OTHER STUDENTS - Ian Francis
 * 
 */



import java.util.ArrayList;

public class MyStack extends ArrayList{
	private ArrayList<Coordinates> arr1=new ArrayList<Coordinates>();
	public MyStack(){
		
	}
	
	
	public boolean isEmpty(){
		return arr1.isEmpty();
	}
	public int getSize(){
		return arr1.size();
	}
	public Coordinates peek(){
		return arr1.get(getSize()-1);
	}
	
	/** Method to push an object on top of the stack 
		This is the only way that you may insert entries into the stack.	
	*/
	@SuppressWarnings("unchecked")
	public void push(Coordinates e){

	//to be implemented; you can use all of ArrayList methods as needed
		
		arr1.add(e);
		
	}


	/** Method to remove element currently at the top of the stack
		this is the only way you are allowed to access and remove
		entries from the stack	
	*/
	public Coordinates pop (){
	//to be implemented - you may use all of ArrayList methods as needed

		Coordinates o=arr1.get(getSize()-1);
		arr1.remove((getSize()-1));
		return o;
	}


}
