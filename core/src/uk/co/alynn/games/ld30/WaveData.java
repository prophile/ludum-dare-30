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
        default:
            if (wave % 5 == 0) {
                return spawnGenericBossWave((wave / 5) - 1);
            } else {
                return spawnGenericWave((wave - 5) / 5);
            }
        }
    }

    private static Wave spawnGenericWave(int i) {
        final int difficulty = i + 1;
        return new Wave() {
            @Override
            protected boolean tick(int tick) {
                if (tick == 0)
                    return false;
                if (tick > difficulty*3)
                    return true;
                if (tick == 6) {
                    while (MathUtils.random(2) == 1) {
                        spawnDestroyer((float)MathUtils.random(0, 100), -5.0f);
                    }
                }
                if (tick % 3 == 0) {
                    for (int i = 0; i < 2; ++i) {
                        spawnInvader((float)MathUtils.random(0, 100), 105.0f, MathUtils.random(2));
                    }
                } else {
                    int nasteroids = MathUtils.random(difficulty);
                    for (int i = 0; i < nasteroids; ++i) {
                        if (MathUtils.random(1) == 1) {
                            spawnAsteroid(-5.0f, MathUtils.random(0.0f, 100.0f), MathUtils.random(-50.0f, 50.0f));
                        } else {
                            spawnAsteroid(105.0f, MathUtils.random(0.0f, 100.0f), MathUtils.random(130.0f, 230.0f));
                        }
                    }
                }
                return false;
            }
        };
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
