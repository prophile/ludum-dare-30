package uk.co.alynn.games.ld30;

public abstract class WaveData {
    public static Wave getWave(int wave) {
        switch (wave) {
        case 1:
            return new Wave() {

                @Override
                protected boolean tick(int tick) {
                    switch (tick) {
                    case 2:
                        spawnAsteroid(-5, 70, -30);
                        break;
                    case 4:
                        spawnAsteroid(105, 20, 150);
                        break;
                    case 5:
                        spawnInvader(50, 105, 2);
                        break;
                    }
                    return tick >= 5;
                }
                
            };
        case 2:
            return new Wave() {
                
                @Override
                protected boolean tick(int tick) {
                    switch (tick) {
                    case 2:
                        spawnAsteroid(-5, 60, 10);
                        break;
                    case 3:
                        spawnAsteroid(-5, 10, 50);
                        spawnAsteroid(105, 70, 160);
                        break;
                    case 5:
                        spawnAsteroid(105, 10, 135);
                        spawnAsteroid(105, 100, 210);
                        break;
                    case 8:
                        spawnInvader(25, 105, 1);
                        spawnInvader(75, 105, 0);
                        spawnAsteroid(-5, 70, -20);
                        break;
                    }
                    return tick >= 8;
                }
                
            };
        case 3:
            return new Wave() {
                
                @Override
                protected boolean tick(int tick) {
                    switch (tick) {
                    case 3:
                        for (int i = 10; i <= 90; i += 10) {
                            spawnAsteroid(-5, i, 0);
                        }
                        break;
                    case 5:
                        for (int i = 15; i <= 85; i += 10) {
                            spawnAsteroid(105, i, 180);
                        }
                        break;
                    }
                    return tick >= 5;
                }
                
            };
        case 4:
            return new Wave() {
                @Override
                protected boolean tick(int tick) {
                    switch (tick) {
                    case 0:
                        spawnInvader(60, 105, 1);
                        break;
                    case 1:
                        spawnInvader(40, 105, 1);
                        break;
                    case 2:
                        spawnInvader(80, 105, 1);
                        break;
                    case 4:
                        spawnAsteroid(105, 65, 165);
                        break;
                    case 5:
                        spawnAsteroid(-5, 20, 20);
                        break;
                    case 6:
                        spawnAsteroid(-5, 90, -70);
                        break;
                    }
                    return tick >= 6;
                }
            };
        case 5:
            return new Wave() {
                protected boolean tick(int tick) {
                    switch (tick) {
                    case 5:
                        spawnDestroyer(50, -5);
                        break;
                    }
                    return tick >= 5;
                }
            };
        case 6:
            return new Wave() {
                protected boolean tick(int tick) {
                    switch (tick) {
                    case 2:
                        spawnInvader(-5, 70, 2);
                        spawnInvader(-5, 30, 1);
                        break;
                    case 5:
                        spawnInvader(105, 70, 2);
                        spawnInvader(105, 30, 0);
                        break;
                    case 8:
                        spawnInvader(-5, 50, 0);
                        break;
                    case 10:
                        spawnInvader(105, 50, 1);
                        break;
                    }
                    return tick >= 10;
                }
            };
        default:
            return new Wave() {
                @Override
                protected boolean tick(int tick) {
                    switch (tick) {
                    case 2:
                        spawnDestroyer(50, -5);
                        break;
                    }
                    return tick >= 2;
                }
            };
        }
    }
}
