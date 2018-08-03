/*

Main{

    BasketBallControl();
}

BasketBallControl{
    startGame()
    checkTime()
    checkIfScored();
    checkGoaltending();
    checkCollision(Point p1,Point P2);
    checkCollision(AnimatedObject p1, AnimatedObject p2);
    movePlayers();
    moveBasketBall();
    checkBounds();
    addScore(Player p)
}

BasketBall extends AnimatedObject{
    String final="Circle"
    getShape();
    setCollisionVelocity(Point p);
    reverseYVelocity();
    reverseXVelocity();
}

Player extends AnimatedObject{
    String final="SemiCircle"
    getShape();
}

AnimatedObject(){
    int xPos;
    int yPos;
    int xVelocity;
    int yVelocity;
    double time;
    double timeSinceLast=0;
    double timeDiff=0;
    getPosition();
    setPosition();
    getVelocity();
    setVelocity();
    move();
    moveVertical();
    moveHorizontal();


}
*/