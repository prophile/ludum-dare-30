package uk.co.alynn.games.ld30;

import com.badlogic.gdx.math.MathUtils;

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
        case 7:
            return new Wave() {
                protected boolean tick(int tick) {
                    switch (tick) {
                    case 1:
                        spawnDestroyer(-5, 105);
                        spawnDestroyer(105, 105);
                        break;
                    case 2:
                        spawnAsteroid(30, 105, -100);
                        break;
                    case 4:
                        spawnInvader(22, -5, 2);
                        spawnInvader(50, -5, 2);
                        spawnInvader(78, -5, 2);
                        break;
                    }
                    return tick >= 4;
                }
            };
        default:
            if (wave % 5 == 0) {
                return spawnGenericBossWave((wave / 5) - 1);
            } else {
                return spawnGenericWave(wave);
            }
        }
    }

    private static Wave spawnGenericWave(int number) {
        int randomNumberLCG = (number * 1103515245) + 12345;
        if (randomNumberLCG < 0) {
            randomNumberLCG = -randomNumberLCG;
        }
        int lower1 = 1 + (randomNumberLCG % (number - 2));
        int lower2 = number - lower1;
        return new SuperimposedWave(getWave(lower1), getWave(lower2));
    }

    private static Wave spawnGenericBossWave(final int difficulty) {
        int wave1 = 1 + MathUtils.random(difficulty + 3);
        int wave2 = 1 + MathUtils.random(difficulty + 2);
        final int destroyers = MathUtils.random(difficulty);
        final int targetPlanet = MathUtils.random(2);
        final int wave1Start = (100 / (wave1 + 1));
        final int wave1End = wave1*wave1Start;
        final int wave1Increment = wave1Start;
        final int wave2Start = (100 / (wave2 + 1));
        final int wave2End = wave2*wave2Start;
        final int wave2Increment = wave2Start;
        return new Wave() {

            @Override
            protected boolean tick(int tick) {
                if (tick == 5) {
                    for (int i = wave1Start; i <= wave1End; i += wave1Increment) {
                        spawnInvader(-5.0f, (float)i, targetPlanet);
                        spawnInvader(105.0f, (float)i, targetPlanet);
                    }
                } else if (tick == 10) {
                    for (int i = 0; i < destroyers; ++i) {
                        spawnDestroyer(MathUtils.random(20, 80), 105f);
                    }
                } else if (tick == 15) {
                    for (int i = wave2Start; i <= wave2End; i += wave2Increment) {
                        spawnInvader(-5.0f, (float)i, targetPlanet);
                        spawnInvader(105.0f, (float)i, targetPlanet);
                    }
                }
                return tick >= 15;
            }
            
        };
    }
}
