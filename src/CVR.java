import java.nio.charset.Charset;
import java.util.*;
import java.lang.String;


public class CVR
{
	private static int threshold;
	private static int keyLength;
	private static boolean higherThanThreshold;
	private static Object cvr;

	//smaller or bigger than threshold
	public CVR(int nbOfRecords)
	{
		if(nbOfRecords >= threshold)
		{
			higherThanThreshold = true;
			cvr = new AVL<String, Vehicle>();
		}
		else
		{
			higherThanThreshold = false;
			cvr = new LinkedList<Vehicle>();
		}
	}
	//set threshold method
	public static void setThreshold(int th)
	{
		if(th < 100 || th > 900000)
		{
			System.out.println("Threshold must be between 100 and 900000!");
		}
		else
		{
			threshold = th;
			System.out.println("Threshold set to " + threshold + ".");
		}
	}

	public static void setKeyLength(int length)
	{
		if(length < 10 || length > 17)
		{
			System.out.println("Key length must be between 10 and 17!");
		}
		else
		{
			keyLength = length;
			System.out.println("Key length set to " + length + ".");
		}
	}

	public static String generate(int n)
	{
		byte[] array = new byte[256]; 
		new Random().nextBytes(array); 
		String randomString = new String(array, Charset.forName("UTF-8")); 
		StringBuffer sb = new StringBuffer(); 
		for (int i = 0; i < randomString.length(); i++) 
		{ 

			char ch = randomString.charAt(i); 

			if (((ch >= 'a' && ch <= 'z') 
					|| (ch >= 'A' && ch <= 'Z') 
					|| (ch >= '0' && ch <= '9')) 
					&& (n > 0)) { 

				sb.append(ch); 
				n--; 
			} 
		} 
		return sb.toString().toUpperCase(); 
	}

	public static void allKeys(Object list)
	{
		// sort tree
		if(higherThanThreshold == true)
		{
			((AVL)cvr).inorder();
		}
		// sort linked list
		else
		{
			((LinkedList)list).sort(Comparator.comparing(Vehicle:: getVIN));

		}
	}

	public static void add(Vehicle v)
	{
		if(higherThanThreshold)
		{
			((AVL)cvr).root = ((AVL)cvr).insert(((AVL)cvr).root, v);
		}
		else
		{
			((LinkedList)cvr).add(v);
		}
	}

	public static void remove(String key)
	{
		boolean found = false;
		if(higherThanThreshold)
		{
			((AVL)cvr).removeNode(((AVL)cvr).root, key);
		}
		else
		{
			ListIterator itr = ((LinkedList)cvr).listIterator();
			while(itr.hasNext())
			{
				if(((Vehicle)itr.next()).getVIN().contentEquals(key))
				{
					found = true;
					itr.remove();
				}
			}
			if (found == false)
			{
				System.out.println("There is no vehicle with that key.");
			}
		}
		
	}
	
	public static void getValues(String key)
	{
		boolean found = false;
		if(higherThanThreshold)
		{
			System.out.println(((AVL)cvr).getNodeValue(((AVL)cvr).root, key));
		}
		else
		{
			Vehicle v;
			ListIterator itr = ((LinkedList)cvr).listIterator();
			while(itr.hasNext())
			{
				v = (Vehicle)itr.next();
				if(v.getVIN().contentEquals(key))
				{
					found = true;
					System.out.println(v);
				}
			}
			if (found == false)
			{
				System.out.println("There is no vehicle with that key.");
			}
		}
	}

	public static void nextKey(String key)
	{
		boolean found = false;
		if(higherThanThreshold)
		{
			System.out.println(((AVL)cvr).getNext(((AVL)cvr).root, key));
		}
		else
		{
			ListIterator itr = ((LinkedList)cvr).listIterator();
			while(itr.hasNext())
			{
				if(((Vehicle)itr.next()).getVIN().contentEquals(key))
				{
					found = true;
					if(itr.hasNext())
					{
						System.out.println(itr.next());
					}
					else
					{
						System.out.println("There is no successor.");
					}
				}
			}
			if (found == false)
			{
				System.out.println("There is no vehicle with that key.");
			}

		}
	}

	public static void prevKey(String key)
	{
		boolean found = false;
		if(higherThanThreshold)
		{
			System.out.println(((AVL)cvr).getPrev(((AVL)cvr).root, key));
		}
		else
		{
			ListIterator itr = ((LinkedList)cvr).listIterator();
			Vehicle vNext;
			while(itr.hasNext())
			{
				vNext = (Vehicle)itr.next();
				if(vNext.getVIN().contentEquals(key))
				{
					found = true;
					if(itr.hasPrevious())
					{
						itr.previous();
						System.out.println(itr.previous());
						break;
					}
					else
					{
						System.out.println("There is no predecessor.");
					}
				}
			}
			if (found == false)
			{
				System.out.println("There is no vehicle with that key.");
			}

		}
	}

	public static void prevAccids(String key)
	{
		boolean found = false;
		if(higherThanThreshold)
		{
			System.out.println(((AVL)cvr).getNodeValue(((AVL)cvr).root, key).getVehicle().getAccidents());
		}
		else
		{
			Vehicle v;
			ListIterator itr = ((LinkedList)cvr).listIterator();
			while(itr.hasNext())
			{
				v = (Vehicle)itr.next();
				if(v.getVIN().contentEquals(key))
				{
					found = true;
					System.out.println(v.getAccidents());
				}
			}
			if (found == false)
			{
				System.out.println("There is no vehicle with that key.");
			}
		}
	}

	public String toString()
	{
		return cvr.toString();
	}

	public static void main(String[] args) 
	{
		System.out.print("Enter the key length: ");
		Scanner sc = new Scanner(System.in);
		int kLength = sc.nextInt();
		System.out.print("Enter the threshold: ");
		int th = sc.nextInt();
		setThreshold(th);
		setKeyLength(kLength);
		Vehicle v1 = new Vehicle(generate(kLength));
		Vehicle v2 = new Vehicle(generate(kLength));
		Vehicle v3 = new Vehicle(generate(kLength));
		Vehicle v4 = new Vehicle(generate(kLength));
		Vehicle v5 = new Vehicle(generate(kLength));
		v3.getAccidents().add("03/11/2020");
		v3.getAccidents().add("03/11/2012");
		v3.getAccidents().add("03/11/2013");
		CVR cvrObj = new CVR(300);
		cvrObj.add(v1);
		cvrObj.add(v2);
		cvrObj.add(v3);
		cvrObj.add(v4);
		allKeys((AVL)cvr);
		System.out.print("Enter the key you want to remove: ");
		String keyToRemove = sc.next();
		cvrObj.remove(keyToRemove);
		allKeys((AVL)cvr);
		System.out.print("Enter the key you want to return: ");
		String keyToReturn = sc.next();
		getValues(keyToReturn);
		System.out.print("Enter the key you want to getNext of: ");
		String keyNext = sc.next();
		cvrObj.nextKey(keyNext);
		System.out.print("Enter the key you want to getPrev of: ");
		String keyPrev = sc.next();
		cvrObj.prevKey(keyPrev);
		System.out.print("Enter the key you want to get accidents of: ");
		String keyAccident = sc.next();
		prevAccids(keyPrev);
	}
}

