import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Stack;

public class AmoebaFamily {

	public static boolean iAmDebugging = true;
	private Amoeba myRoot = null;
	public static int myDepth = 0;
	public static int mySize = 0;

	public AmoebaFamily(String name) {
		myRoot = new Amoeba(name, null);
	}

	public String getMyRootToString() {
		return myRoot.toString();
	}

	private static class Amoeba {

		public String myName; // amoeba's name
		public Amoeba myParent; // amoeba's parent
		public ArrayList<Amoeba> myChildren; // amoeba's children

		public Amoeba(String name, Amoeba parent) {
			myName = name;
			myParent = parent;
			myChildren = new ArrayList<Amoeba>();
		}

		public String toString() {
			return myName;
		}

		public Amoeba parent() {
			return myParent;
		}

		// Add an amoeba with the given name as this amoeba's youngest child.
		public void addChild(String childName) {
			Amoeba child = new Amoeba(childName, this);
			myChildren.add(child);
		}
	}

	// Add a new amoeba named childName as the youngest child
	// of the amoeba named parentName.
	// Precondition: the amoeba family contains an amoeba named parentName.
	public void addChild(String parentName, String childName) {
		if (myRoot != null) {
			AmoebaFamily.addChildHelper(parentName, childName, myRoot);
		}
	}

	public static void addChildHelper(String parentName, String childName,
			Amoeba currentAmoeba) {
		if (currentAmoeba.myName.equals(parentName)) {
			currentAmoeba.addChild(childName); // When myName is myRoot
		} else {
			Iterator<Amoeba> iter = currentAmoeba.myChildren.iterator();
			while (iter.hasNext()) {
				AmoebaFamily.addChildHelper(parentName, childName, iter.next());
			}
			/*
			 * Here is another way to loop through all of the children for (int
			 * i = 0; i< currentAmoeba.myChildren.size(); i++) {
			 * AmoebaFamily.addChildHelper(parentName, childName,
			 * currentAmoeba.myChildren.get(i)); }
			 */
		}
	}

	// Makes all Amoeba names only lower case letters.
	public void makeNamesLowercase() {
		if (myRoot != null) {
			AmoebaFamily.makeNamesLowercaseHelper(myRoot);
		}
	}

	// Static helper method for makeNamesLowercase
	public static void makeNamesLowercaseHelper(Amoeba currentAmoeba) {
		if (currentAmoeba.myChildren == null)
			currentAmoeba.myName = currentAmoeba.myName.toLowerCase();
		else {
			currentAmoeba.myName = currentAmoeba.myName.toLowerCase();
			Iterator<Amoeba> iter = currentAmoeba.myChildren.iterator();
			while (iter.hasNext()) {
				AmoebaFamily.makeNamesLowercaseHelper(iter.next());
			}
		}
	}

	// R eplaces the name of an amoeba named currentName with the name newName.
	// Precondition: the amoeba family contains an amoeba named currentName.
	public void replaceName(String currentName, String newName) {
		if (myRoot != null) {
			AmoebaFamily.replaceNameHelper(currentName, newName, myRoot);
		}
	}

	// Static helper method for replaceName
	public static void replaceNameHelper(String currentName, String newName,
			Amoeba currentAmoeba) {
		if (currentAmoeba.myName.equals(currentName))
			currentAmoeba.myName = newName;
		else {
			Iterator<Amoeba> iter = currentAmoeba.myChildren.iterator();
			while (iter.hasNext()) {
				if (currentAmoeba.myName.equals(currentName))
					currentAmoeba.myName = newName;
				else
					replaceNameHelper(currentName, newName, iter.next());
			}
		}
	}

	// Print the names of all amoebas in the family.
	// later you will write print() that has more interesting formatting
	public void printFlat() {
		if (myRoot != null) {
			AmoebaFamily.printFlatHelper(myRoot);
		}
	}

	// Static helper method for printFlat
	public static void printFlatHelper(Amoeba currentAmoeba) {
		if (currentAmoeba.myChildren == null)
			System.out.println(currentAmoeba.myName);
		else {
			System.out.println(currentAmoeba.myName);
			Iterator<Amoeba> iter = currentAmoeba.myChildren.iterator();
			while (iter.hasNext()) {
				AmoebaFamily.printFlatHelper(iter.next());
			}
		}
	}

	// Print the names of all amoebas in the family.
	// Names should appear in preorder, with children's names
	// printed oldest first.
	// Members of the family constructed with the main program above
	// should be printed in the following sequence:
	// Amos McCoy, mom/dad, me, Mike, Bart, Lisa, Homer, Marge,
	// Bill, Hilary, Fred, Wilma
	public void print() {
		if (myRoot != null) {
			AmoebaFamily.printHelper(myRoot);
		}
	}

	public static void printHelper(Amoeba currentAmoeba) {
		if (currentAmoeba.myChildren == null) {
			depthIndenter(myDepth);
			System.out.println(currentAmoeba.myName);
		} else {
			depthIndenter(myDepth);
			System.out.println(currentAmoeba.myName);
			myDepth++;
			Iterator<Amoeba> iter = currentAmoeba.myChildren.iterator();
			while (iter.hasNext()) {
				depthIndenter(myDepth);
				AmoebaFamily.printHelper(iter.next());
			}
			myDepth--;
		}
	}

	public static void depthIndenter(int myDepth) {
		while (myDepth > 0) {
			System.out.print("    ");
			myDepth--;
		}
	}

	// returns the length of the longest name in the Amoeba Family
	public int longestNameLength() {
		if (myRoot != null) {
			return AmoebaFamily.longestNameLengthHelper(myRoot);
		}
		return 0;

	}

	// Static helper method for longestName
	public static int longestNameLengthHelper(Amoeba currentAmoeba) {
		int maxLengthSeen = currentAmoeba.myName.length();
		Iterator<Amoeba> iter = currentAmoeba.myChildren.iterator();
		while (iter.hasNext()) {
			int childMaxLengthSeen = AmoebaFamily.longestNameLengthHelper(iter
					.next());
			if (childMaxLengthSeen > maxLengthSeen) {
				maxLengthSeen = childMaxLengthSeen;
			}
		}
		return maxLengthSeen;
	}

	// instead of returning the length of the longest name, this method should
	// return the name that is longest.
	public String longestName() {
		if (myRoot != null) {
			return AmoebaFamily.longestName(myRoot);
		}
		return myRoot.myName;
	}

	// Static helper method for longestName
	public static String longestName(Amoeba currentAmoeba) {
		String maxLengthName = currentAmoeba.myName;
		Iterator<Amoeba> iter = currentAmoeba.myChildren.iterator();
		while (iter.hasNext()) {
			String childMaxLengthName = AmoebaFamily.longestName(iter.next());
			if (childMaxLengthName.length() > maxLengthName.length()) {
				maxLengthName = childMaxLengthName;
			}
		}
		return maxLengthName;
	}

	public int size() {
		if (myRoot != null) {
			AmoebaFamily.sizeHelper(myRoot);
		}
		return mySize;
	}

	// Static helper method for longestName
	public static void sizeHelper(Amoeba currentAmoeba) {
		if (currentAmoeba.myChildren == null)
			mySize++;
		else {
			mySize++;
			Iterator<Amoeba> iter = currentAmoeba.myChildren.iterator();
			while (iter.hasNext()) {
				AmoebaFamily.sizeHelper(iter.next());
			}
		}
	}

	public String busiestAmoeba() {
		if (myRoot != null) {
			return AmoebaFamily.longestName(myRoot);
		}
		return myRoot.myName;
	}

	public static String busiestAmoebaHelper(Amoeba currentAmoeba) {
		String maxLengthName = currentAmoeba.myName;
		Iterator<Amoeba> iter = currentAmoeba.myChildren.iterator();
		while (iter.hasNext()) {

			String childMaxLengthName = AmoebaFamily.longestName(iter.next());
			if (childMaxLengthName.length() > maxLengthName.length()) {
				maxLengthName = childMaxLengthName;
			}
		}
		return maxLengthName;
	}

	public boolean isBinary() {
		return isBinary(myRoot);
	}

	private static boolean isBinary(Amoeba a) {
		if (a.myChildren.size() > 2)
			return false;
		Iterator<Amoeba> e = a.myChildren.iterator();
		while (e.hasNext()) {
			Amoeba child = e.next();

			// Or we can do Iterator<Amoeba> without casting
			if (!isBinary(child))
				return false;
		}
		return true;
	}

	// Return an depth-first iterator(Stack) of the amoeba family.
	public Iterator<Amoeba> getDepthFirstIterator() {
		return new depthFirstAmoebaIterator();
	}

	// Return an breadth-first iterator(Queue) of the amoeba family.
	public Iterator<Amoeba> getBreadthFirstIterator() {
		return new breadthFirstAmoebaIterator();
	}

	public static void main(String[] args) {
		AmoebaFamily family = new AmoebaFamily("Amos McCoy");
		family.addChild("Amos McCoy", "mom/dad");
		family.addChild("Amos McCoy", "auntie");
		family.addChild("mom/dad", "me");
		family.addChild("mom/dad", "Fred");
		family.addChild("mom/dad", "Wilma");
		family.addChild("me", "Mike");
		family.addChild("me", "Homer");
		family.addChild("me", "Marge");
		family.addChild("Mike", "Bart");
		family.addChild("Mike", "Lisa");
		family.addChild("Marge", "Bill");
		family.addChild("Marge", "Hilary");

		if (iAmDebugging) {
			family.printFlat();
		}

		System.out.println("Here's the family:");
		family.print();

		System.out.println("");
		System.out.println("Here it is again!: ~ Depth First~ ");
		Iterator<Amoeba> depthIter = family.getDepthFirstIterator();
		while (depthIter.hasNext()) {
			System.out.println(depthIter.next());
		}

		System.out.println("Here it is again!: ~ Breadth First~ ");
		Iterator<Amoeba> breadthIter = family.getBreadthFirstIterator();
		while (breadthIter.hasNext()) {
			System.out.println(breadthIter.next());
		}

	}

	public class depthFirstAmoebaIterator implements Iterator<Amoeba> {
		// Amoebas in the family are enumerated in preorder,
		// with children enumerated oldest first.
		// Members of the family constructed with the main program above
		// should be enumerated in the following sequence:
		// Amos McCoy, mom/dad, me, Mike, Bart, Lisa, Homer, Marge,
		// Bill, Hilary, Fred, Wilma
		// Complete enumeration of a family of N amoebas should take
		// O(N) operations.

		// You will supply the details of this class in a future lab.
		private Stack<Amoeba> fringe = new Stack<Amoeba>();

		public depthFirstAmoebaIterator() {
			if (myRoot != null) {
				fringe.push(myRoot);
			}
		}

		public boolean hasNext() {
			return !fringe.empty(); // check if there is something in fringe.
		}

		public Amoeba next() {
			if (!hasNext()) {
				throw new NoSuchElementException("tree ran out of elements");
			}

			// Be careful! index is one less than the size.
			Amoeba currentAmoeba = fringe.pop();
			if (currentAmoeba.myChildren != null) {
				int childSize = currentAmoeba.myChildren.size();
				while (childSize > 0) {
					fringe.push(currentAmoeba.myChildren.get(childSize - 1));
					childSize--;
				}
			}
			return currentAmoeba;
		}

		public void remove() {
			// not used
		}

	} // end of depth first AmoebaIterator nested class

	public class breadthFirstAmoebaIterator implements Iterator<Amoeba> {

		// This will result in amoeba names being returned breadth first. That
		// is the name of the root of the family will be returned first, then
		// the names of its children(oldest first), then the names of all their
		// children, and so on.
		// Members of the family constructed with the main program above
		// should be enumerated in the following sequence:
		// Amos McCoy, mom/dad, auntie, me, Fred, Wilma, Mike, Homer, Marge,
		// Bart, Lisa, Bill, Hilary
		// Complete enumeration of a family of N amoebas should take
		// O(N) operations.

		// You will supply the details of this class in a future lab.

		// Since queue is an interface, we need a implementing class.
		// In this case, I used linked list
		private Queue<Amoeba> fringe = new LinkedList<Amoeba>();

		public breadthFirstAmoebaIterator() {
			if (myRoot != null) {
				fringe.add(myRoot);
			}
		}

		public boolean hasNext() {
			return !fringe.isEmpty(); // check if there is something in fringe.
		}

		public Amoeba next() {
			if (!hasNext()) {
				throw new NoSuchElementException("tree ran out of elements");
			}

			// Be careful! index is one less than the size.
			Amoeba currentAmoeba = fringe.poll();
			if (currentAmoeba.myChildren != null) {
				int childSize = currentAmoeba.myChildren.size();
				int i = 0;
				while (i < childSize) {
					fringe.add(currentAmoeba.myChildren.get(i));
					i++;
				}
			}
			return currentAmoeba;
		}

		public void remove() {
			// not used
		}

	} // end of breadth first AmoebaIterator nested class

	public int height() {
		if (myRoot != null) {
			return heightHelper(myRoot);
		}
		return 0;
	}

	private static int heightHelper(Amoeba x) {
		if (x.myChildren.isEmpty()) {
			return 1;
		} else {
			int bestSoFar = 1;
			Iterator<Amoeba> iter = x.myChildren.iterator();
			while (iter.hasNext()) {
				Amoeba child = iter.next();
				bestSoFar = Math.max(heightHelper(child), bestSoFar);
			}
			bestSoFar++;
			return bestSoFar;
		}
	}
} // closes definition of AmoebaFamily class
