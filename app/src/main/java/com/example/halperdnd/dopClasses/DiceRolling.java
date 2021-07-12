package com.example.halperdnd.dopClasses;

import java.util.Random;

public class DiceRolling  {
   static public int diceRoll(int dice) {
       final Random random = new Random();
        int ress = random.nextInt(dice);
        return  ress == 0 ? 1 : ress;
    }
}
