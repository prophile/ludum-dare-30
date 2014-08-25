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
