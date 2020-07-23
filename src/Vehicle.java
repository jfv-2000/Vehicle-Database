import java.util.*;
public class Vehicle 
{
	private String vin;
	private ArrayList<String> accidents;
	public Vehicle(String vin)
	{
		this.vin = vin;
		accidents = new ArrayList<String>();
	}
	
	public String getVIN()
	{
		return vin;
	}
	
	public ArrayList getAccidents()
	{
		return accidents;
	}
	
	public String toString() 
	{
		return "VIN: " + vin + " | Record History:  " + accidents + "\n";
	}
}
