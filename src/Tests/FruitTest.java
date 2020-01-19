package Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import gameClient.Fruit;

class FruitTest {

	@Test
	void GetTest() {
		Fruit f=new Fruit(12,1,null);
		
		assertEquals (f.getValue(),12);
		
		assertEquals (f.getType(),1);
	}

	@Test
	void SetTest() {
		Fruit f=new Fruit(12,1,null);
		
		f.setValue(6);
		f.setType(-1);
		
		assertEquals (f.getValue(),6);
		
		assertEquals (f.getType(),-1);
	}

	
	
}
