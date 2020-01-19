package Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import gameClient.Robot;

class RobotTest {

	@Test
	void GetTest() {
		Robot rob=new Robot(3, 4, 8, 10, 10, null);
		assertEquals (rob.getId(),3);
		
		assertEquals (rob.getValue(),4);

		assertEquals (rob.getSrc(),8);

		assertEquals (rob.getDest(),10);

		assertEquals (rob.getSpeed(),10);

	}


	@Test
	void SetTest() {
		Robot rob=new Robot(3, 4, 8, 10, 10, null);
		rob.setId(13);
		rob.setValue(7);
		rob.setSrc(2);
		rob.setDest(6);
		rob.setSpeed(45);
		
		assertEquals (rob.getId(),13);
		
		assertEquals (rob.getValue(),7);

		assertEquals (rob.getSrc(),2);

		assertEquals (rob.getDest(),6);

		assertEquals (rob.getSpeed(),45);

	}

}
