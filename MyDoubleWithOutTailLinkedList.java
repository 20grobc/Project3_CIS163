package Project3;

import java.io.Serializable;
import java.util.Random;

public class MyDoubleWithOutTailLinkedList implements Serializable {

	private DNode top;

	public MyDoubleWithOutTailLinkedList() {
		top = null;
	}

	// This method has been provided and you are not permitted to modify
	public int size() {
		if (top == null)
			return 0;

		int total = 0;
		DNode temp = top;
		while (temp != null) {
			total++;
			temp = temp.getNext();
		}

		int totalBack = 0;
		temp = top;
		while (temp.getNext() != null) {
			temp = temp.getNext();
		}

		while (temp != null) {
			totalBack++;
			temp = temp.getPrev();
		}

		if (total != totalBack) {
			throw new RuntimeException("next links " + total + " do not match prev links " + totalBack);
		}

		return total;

	}

	// This method has been provided and you are not permitted to modify
	public void clear () {
		Random rand = new Random();
		while (size() > 0) {
			int number = rand.nextInt(size());
			remove(number);
		}
	}

	/******************************************************************
	 *
	 * Your task is to complete this method.  Have the following
	 * ordering:
	 * Sort by Games first (ordered by dueDate), then by Consoles second
	 * (ordered by dueDate).
	 * Once you have Task 2 completed, sort by the renter's name if the
	 * dueDates are equal to complete Task 3.
	 *
	 */
	public void add(Rental s) {
		DNode temp = top;

		// no list CASE 1
		if (top == null) {
			top = new DNode(s, null,null);
			return;
		}

		// s is a Game, and s goes on top
		if (s instanceof Game && top.getData().getDueBack().after(s.dueBack)) {
			top = new DNode(s, top, null);
			top.getNext().setPrev(top);
			return;
		}

		//s is a Game, equals top data so check name CASE3
		if (s instanceof Game && top.getData().getDueBack().equals(s.dueBack)) {
			if(top.getData().nameOfRenter.compareTo(s.nameOfRenter)>0){
				top = new DNode(s, top, null);
				top.getNext().setPrev(top);
				return;
			}
		}


		//if s is a console and s goes on top CASE4
		if(s instanceof Console && top.getData().getDueBack().after(s.dueBack) && top.getData() instanceof Console){
			top = new DNode(s, top, null);
			top.getNext().setPrev(top);
			return;
		}
		//if s is a console, equals top data so check name
		if(s instanceof Console && top.getData().getDueBack().equals(s.dueBack) && top.getData() instanceof Console){
			if(top.getData().nameOfRenter.compareTo(s.nameOfRenter)>0) {
				top = new DNode(s, top, null);
				top.getNext().setPrev(top);
				return;
			}
		}

		// s is a game, and s does not go on top CASE5
		if(s instanceof Game) {
			DNode curr = top;
			while (curr.getNext() != null && curr.getNext().getData().getDueBack().before(s.dueBack) && curr.getNext().getData() instanceof Game) {
				curr = curr.getNext();
			}
			if (curr.getNext() == null) {
				DNode added = new DNode(s, null, curr);
				curr.setNext(added);
				return;
			}
			//add if equals code here PART3
			DNode added = new DNode(s, curr.getNext(), curr);
			curr.getNext().setPrev(added);
			curr.setNext(added);
			return;
		}

		// s is a console, and s does not go on top
		if(s instanceof Console){
			DNode start = top;
			while(start.getNext() != null && start.getNext().getData() instanceof Game){
				start = start.getNext();
			}
			if(start.getNext() == null){
				DNode added = new DNode(s, null, start);
				start.setNext(added);
				return; //Case where the list is full of just games and s goes on bottom
			}
			//if start.getNext().getData() instanceof Console <-- Has to be true
			DNode curr = start;
			//while the next item isn't null or come after the dueBack date of s
			while(curr.getNext() != null && curr.getNext().getData().getDueBack().before(s.dueBack)){
				curr = curr.getNext(); //keep going
			}
			if(curr.getNext() == null){
				DNode added = new DNode(s, null, start);
				start.setNext(added);
				return; //Case where there are consoles in list and s goes on bottom
			}
			//add if equals here PART 3
			DNode added = new DNode (s, curr.getNext(), curr);
			start.setNext(added);
			added.getNext().setPrev(added);
			return; //Case where s goes somewhere in the middle with consoles
		}



		return;
	}

	/******************************************************************
	 *
	 * Your task is to complete this method.
	 */
	public Rental remove(int index) {
		// TODO: More code goes here52

		//case 0 no list exists
		if (top == null)
			return null;

		//case 1 index isn't valid
		if (index > getLen() || index <0)
			throw new IllegalArgumentException();

		DNode temp = top;

		//case 2 deleting first item in list
		if (index == 0)
		{
			DNode deleting = top;
			top = temp.getNext();   // Change top, setting it to null
			return deleting.getData(); //returns the deleted data
		}

		//case 3 the index to be deleted is somewhere else in the list

		DNode prev = temp;
		DNode curr = temp.getNext();

		// Find previous node of the node to be deleted, as well as the node to be deleted
		for (int i=1; curr!=null && i<index; i++) {
			prev = prev.getNext();
			curr = curr.getNext();
		}

		prev.setNext(curr.getNext()); //deletes curr
		return curr.getData(); //return what was deleted
	}

	/******************************************************************
	 *
	 * Your task is to complete this method.
	 */
	public Rental get(int index) {
		// TODO: More code goes here.

		if (top == null)
			return null;

		if (index > getLen() || index <0)
			throw new IllegalArgumentException();

		DNode temp = top;
		for (int i = 0; i < index; i++)
			temp = temp.getNext();

		return temp.getData();
	}

	//returns length of a linked list. Helper Function
	public int getLen() {

		int count = 0;
		DNode temp =  top;
		while (temp != null) {
			count++;
			temp=temp.getNext();

		}
		return count;
	}

	public void printList(){
		DNode temp = top;
		while(temp != null){
			System.out.println(temp.getData());
			temp = temp.getNext();
		}
	}
	public void display() {
		DNode temp = top;
		while (temp != null) {

			System.out.println(temp.getData());
			temp = temp.getNext();
		}
	}





	public String toString() {
		return "DLL {" +
				"top=" + top +
				", size=" + size() +
				'}';
	}

}