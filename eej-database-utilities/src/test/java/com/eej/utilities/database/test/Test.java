/**
 * 
 */
package com.eej.utilities.database.test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author DOIBALMI
 *
 */
public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException{
		// TODO Auto-generated method stub
		Class theClazz = TestObject.class;
		Field[] fields = theClazz.getDeclaredFields();
		for(Field f: fields){
			Class cInteger = Class.forName("java.lang.Integer");
			//Class cInt = Class.forName("int");
			if(f.getType().isAssignableFrom(cInteger)){
				System.out.println(f.getName() + " is Integer");
			}/*else if(f.getType().isAssignableFrom(cInt)){
				System.out.println(f.getName() + " is int");
			}*/
			System.out.println(Integer.TYPE);
			
			
		}
		Integer i = 10;
		for(Field field : Class.forName("java.lang.Integer").getFields()){
			System.out.println("field name = " + field.getName());
			if(field.getName().equals("TYPE")){
				System.out.println(">>>>>>>> field TYPE found !!!!!!!!!!!!!!!");
				System.out.println(" value = " + field.get(i));
			}
		}
		
		Class clazz2 = Class.forName("java.lang.Integer");
		for(Field theField: fields){
			for(Field field : clazz2.getFields()){
				System.out.println("2 . field name = " + field.getName());
				if(field.getName().equals("TYPE")){
					System.out.println("2. >>>>>>>> field TYPE found !!!!!!!!!!!!!!!");
					try{
						if(field.get(null).equals(theField.getType())){
							System.out.println("IGUALES!!!! " + theField.getType());
						}
					}catch(Throwable t){
						System.out.println("Error " + t.getMessage());
						t.printStackTrace(System.out);
					}
				}
			}
			//Class.forName(className).newInstance()												
		}
	}

}
