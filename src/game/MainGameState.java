package game;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Iterator;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Sound;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;
import org.newdawn.slick.state.StateBasedGame;

public class MainGameState extends SimpleGameState
{
  private static final int EMITTER_BOOM = 0;
  private static final int EMITTER_BLOOD = 1;
  private static final int EMITTER_BARREL = 2;
  private static final int NUM_EMITTERS = 15;
  private static final int FIRING_DELAY = 120;
  private static final int FIRING_DELAY_SHOTGUN = 500;
  private static final int RELOAD_TIME = 1500;
  private static final int MAX_AMMO = 20;
  private boolean soundEnabled = true;
  private int id;
  private Movable player = new Movable(400.0F, 300.0F, new Circle(0.0F, 0.0F, 20.0F));

  private float camX = 0.0F;
  private float camY = 0.0F;
  private Image crosshair;
  private Polygon outer;
  private ArrayList<Polygon> inners = new ArrayList();

  private ArrayList<Line> lines = new ArrayList();

  private ArrayList<Movable> movables = new ArrayList();
  private ArrayList<Projectile> projectiles = new ArrayList();
  private Image enemyTop;
  private Image enemyBottom;
  private Image enemyBottomFlipped;
  private Image playerTop;
  private Image playerBottom;
  private Image playerBottomFlipped;
  private Image bullet;
  private Image groundTexture;
  private Image groundTexture2;
  private Image barrel;
  private Image medpack;
  private Image extraAmmo;
  private Image logo;
  private int deltaCount = 0;

  private int hp = 100;
  private Sound soundHit;
  private Sound soundHit2;
  private Sound soundShoot;
  private Sound soundExplosion;
  private Sound soundPickup;
  private Sound soundWin;
  private TrueTypeFont hpFont;
  private TrueTypeFont messageFont;
  private TrueTypeFont smallFont;
  private TrueTypeFont buttonFont;
  private ParticleSystem particleSystem;
  private ConfigurableEmitter[] boom = new ConfigurableEmitter[15];
  private ConfigurableEmitter[] blood = new ConfigurableEmitter[15];
  private ConfigurableEmitter[] barrelboom = new ConfigurableEmitter[7];

  private int boomEmitterCount = 0;
  private int bloodEmitterCount = 0;
  private int barrelEmitterCount = 0;

  private int shootTimer = 0;
  private int reloadTimer = 0;
  private int ammo = 20;
  private int shotgunAmmo = 0;
  private int lastLevelShotgunAmmo = 0;

  private int level = 0;
  private float goalX;
  private float goalY;
  private boolean won;
  private boolean playerDead;
  private boolean titleScreen = true;

  private int kills = 0;
  private int killsTotal = 0;

  private int animationCounter = 0;
  private boolean animationStep = true;

  private int hpTimer = 0;

  private int fpsDropped = 0;

  private int quality = 0;

  private static String[] levels = { "320,215,520,205,645,320,660,425,680,550,600,635,615,705,560,755,580,845,500,980,610,1105,745,1035,950,1020,995,870,900,730,875,535,950,370,1095,235,1310,215,1715,280,1910,400,2030,570,1960,770,1680,865,1465,745,1195,660,1145,725,1240,815,1415,860,1490,990,1465,1160,1320,1305,1070,1230,805,1315,705,1490,515,1565,250,1535,115,1240,125,920,140,780,215,655,185,405,//440,350//1785,615//&&E,1610,395//E,1620,700//B,1270,1030//", "165,1195,345,920,720,870,795,690,755,550,590,495,550,245,755,140,1085,210,1165,445,1445,440,1620,325,1990,435,2125,675,2520,810,2620,690,2770,585,3090,660,3430,1100,3385,1265,3315,1340,3045,1410,2890,1495,2995,1655,3125,1715,3155,1940,2910,2105,2685,2080,2345,1975,2235,2085,2215,2205,2055,2450,1710,2445,1455,2485,1300,2675,1060,2605,895,2490,790,2410,650,2495,455,2620,295,2610,220,2555,150,2435,110,2145,315,1960,430,1780,340,1465,210,1430,//1510,940,1675,815,1915,885,1930,1020,1780,1285,1530,1145,//1015,1205,1195,1235,1240,1460,1450,1635,1565,1800,1420,1925,1215,1840,1095,1610,945,1590,835,1320,//2440,1290,2540,1325,2615,1275,2670,1140,2750,1080,2780,1155,2745,1280,2670,1390,2580,1455,2480,1475,2330,1475,2170,1290,2215,1180,2335,1165,//815,340//3040,1885//&&B,915,2080//B,1125,940//B,1600,1385//E,1720,555//B,2365,905//E,3075,1005//B,2765,1600//E,2670,1870//B,2025,1960//E,1755,2225//B,1125,2315//E,470,1135//B,660,1240//B,505,2165//B,2725,1855//B,1525,520//E,1260,770//B,1760,1725//B,1620,2135//B,2370,1670//E,1980,1580//E,750,2020//E,2340,1005//", "165,380,340,205,625,250,695,455,865,495,945,365,1220,185,1650,340,1705,470,1855,415,2025,645,1805,930,2335,1735,1865,2155,1765,2680,1630,2830,1350,2820,1230,2600,1210,2340,945,2100,660,2260,280,1935,260,1365,400,1115,175,805,//840,900,945,760,1110,840,1100,910,1000,940,960,1015,1005,1090,1070,1100,1135,1065,1245,1065,1265,1175,1120,1260,930,1230,855,1075,//1435,1630,1510,1540,1630,1605,1575,1720,1425,1720,//635,1550,720,1405,920,1415,985,1565,885,1720,680,1725,//370,415//1465,2710//&&E,770,1930//E,1815,1455//E,1350,2250//E,1565,2640//E,1655,2395//B,695,795//B,1540,1110//B,1220,1605//B,745,2005//B,1610,2215//E,525,1240//B,425,1610//E,1200,1515//E,1425,690//E,1720,1925//", "220,780,505,635,820,705,1075,590,1315,290,1785,375,2205,230,2680,305,2975,430,3240,230,3510,430,3585,695,3315,1150,3045,1180,2960,1435,3225,1635,2985,1785,2605,1670,2265,1755,1985,1885,1325,1835,1090,1955,880,1865,630,1895,595,2070,745,2120,825,2230,1150,2215,1375,2395,1700,2325,1830,2215,2030,2355,2310,2335,2390,2180,2320,1955,2450,1830,2660,1935,2800,2135,2835,2320,2960,2430,3120,2455,3275,2280,3295,2105,3525,1950,3675,1925,3720,2065,3620,2120,3585,2260,3700,2450,3735,2655,3565,2845,3145,2780,2870,2925,2555,2815,2390,2650,2235,2670,2140,2715,2085,2675,2005,2740,1925,2710,1640,2760,1260,2720,1080,2615,835,2685,725,2795,490,2755,215,2570,295,2140,150,1760,555,1415,795,1435,1145,1595,1320,1455,1320,1300,1665,1225,1925,1335,2140,1020,2445,1040,2510,1195,2725,1030,2730,830,2205,620,1940,720,1795,920,1320,1000,1035,1100,965,1220,730,1310,455,1280,190,1070,//2185,1325,2280,1250,2345,1320,2325,1430,2240,1405,//3415,2625//610,970//&&E,2840,2715//E,2530,2000//B,2925,2685//E,495,2365//E,630,2535//B,620,2340//B,770,2460//B,590,2605//B,405,2415//B,1400,2625//E,765,1580//B,1180,1700//B,1630,1615//E,1980,1595//B,2110,1435//E,2680,1395//B,2370,1205//E,2820,605//B,2500,465//E,1400,510//B,1455,685//B,1370,875//B,1595,810//B,3160,655//B,3350,875//B,3100,1025//B,2935,885//B,3265,475//B,2885,1540//B,2690,1560//B,1945,1730//B,460,890//B,455,1010//B,525,1105//B,530,820//E,710,965//E,1940,520//E,3080,860//E,1455,1640//E,500,1715//E,1085,2435//E,1820,2505//E,2590,2425//", "275,730,855,345,1755,365,2070,805,2415,1010,2555,1510,2120,1940,1780,2370,1225,2255,550,1785,250,1260,//835,530//1745,2255//&&B,1355,815//E,1370,945//B,855,915//E,670,980//B,775,1415//E,1010,1415//B,1570,1205//E,1325,1200//B,1495,750//E,1905,855//B,2065,1225//E,1710,1540//B,1145,1780//E,555,1460//B,570,1170//E,1015,1120//E,1180,725//E,1610,535//E,1760,1045//E,1315,1565//E,790,1695//E,1330,2090//B,1525,1930//E,1485,1685//B,1525,1440//B,1290,1335//B,1925,1340//B,1815,900//E,2275,1180//B,2150,1530//E,1955,1125//E,1970,1700//E,2285,1465//E,1780,1905//B,1705,1710//E,1045,1975//B,395,880//E,425,755//E,750,1205//E,345,1090//E,1355,1885//E,1030,1655//E,1710,1290//E,1120,905//E,1430,535//E,1550,2165//E,1840,2075//E,2035,1435//" };

  public MainGameState() {
    this.id = ((int)System.currentTimeMillis());
  }
  public int getID() {
    return this.id;
  }

  public void init(GameContainer container, StateBasedGame game) {
    ConfigurableEmitter boomEmitter = null;
    ConfigurableEmitter bloodEmitter = null;
    ConfigurableEmitter barrelEmitter = null;
    try
    {
      this.crosshair = new Image("res/crosshair.gif");
      this.enemyTop = new Image("res/enemyTop.gif");
      this.enemyBottom = new Image("res/enemyBottom.gif");
      this.enemyBottomFlipped = this.enemyBottom.getFlippedCopy(true, false);
      this.playerTop = new Image("res/playerTop.gif");
      this.playerBottom = new Image("res/playerBottom.gif");
      this.playerBottomFlipped = this.playerBottom.getFlippedCopy(true, false);
      this.bullet = new Image("res/bullet.gif");
      this.groundTexture = new Image("res/groundTexture3.gif");
      this.groundTexture2 = new Image("res/groundTexture2.gif");
      this.barrel = new Image("res/barrel.gif");
      this.medpack = new Image("res/medpack.gif");
      this.extraAmmo = new Image("res/ammo.gif");
      this.logo = new Image("res/cavernousshooter.png");

      this.soundHit = new Sound("res/hit.ogg");
      this.soundHit2 = new Sound("res/hit3.ogg");
      this.soundShoot = new Sound("res/shoot.ogg");
      this.soundExplosion = new Sound("res/explosion3.ogg");
      this.soundPickup = new Sound("res/pickup2.ogg");
      this.soundWin = new Sound("res/win3.ogg");

      boomEmitter = ParticleIO.loadEmitter("res/boom.xml");
      bloodEmitter = ParticleIO.loadEmitter("res/blood.xml");
      barrelEmitter = ParticleIO.loadEmitter("res/barrelboom.xml");

      this.hpFont = new TrueTypeFont(new Font("Arial", 0, 48), true);
      this.messageFont = new TrueTypeFont(new Font("Arial", 1, 48), true);
      this.smallFont = new TrueTypeFont(new Font("Arial", 0, 16), true);
      this.buttonFont = new TrueTypeFont(new Font("Arial", 1, 24), true);

      this.particleSystem = new ParticleSystem("res/particle2.png");
      this.particleSystem.setRemoveCompletedEmitters(true);
      this.particleSystem.setUsePoints(false);
    }
    catch (Exception se)
    {
      se.printStackTrace();
    }container.setMouseGrabbed(true);

    for (int i = 0; i < 15; i++) {
      this.boom[i] = boomEmitter.duplicate();
      this.blood[i] = bloodEmitter.duplicate();
    }
    for (int i = 0; i < 7; i++) {
      this.barrelboom[i] = barrelEmitter.duplicate();
    }

    initLevel(this.level);
  }

  public void initLevel(int level)
  {
    this.inners.clear();
    this.movables.clear();
    this.projectiles.clear();
    this.lines.clear();
    this.won = false;
    this.playerDead = false;
    this.hp = 100;
    this.movables.add(this.player);
    this.ammo = 20;
    this.reloadTimer = 0;
    this.kills = 0;

    this.shotgunAmmo = this.lastLevelShotgunAmmo;

    String[] levelParts = levels[level].split("&&");
    String[] tmp = levelParts[0].split("//");
    String[] coords = tmp[0].split(",");
    this.outer = new Polygon();
    for (int i = 0; i < coords.length - 1; i += 2) {
      this.outer.addPoint(Integer.parseInt(coords[i]), Integer.parseInt(coords[(i + 1)]));
    }
    this.inners.clear();
    int j;
    for (int i = 1; i < tmp.length - 2; i++) {
      Polygon p = new Polygon();
      coords = tmp[i].split(",");
      for (j = 0; j < coords.length - 1; j += 2) {
        p.addPoint(Integer.parseInt(coords[j]), Integer.parseInt(coords[(j + 1)]));
      }
      this.inners.add(p);
    }

    coords = tmp[(tmp.length - 2)].split(",");
    this.player.x = Integer.parseInt(coords[0]);
    this.player.y = Integer.parseInt(coords[1]);

    coords = tmp[(tmp.length - 1)].split(",");
    this.goalX = Integer.parseInt(coords[0]);
    this.goalY = Integer.parseInt(coords[1]);

    if (levelParts.length > 1) {
      String[] objs = levelParts[1].split("//");
      for (obj : objs) {
        String[] objDetails = obj.split(",");
        if (objDetails[0].equals("E")) {
          Enemy enemy = new Enemy(Integer.parseInt(objDetails[1]), Integer.parseInt(objDetails[2]), new Circle(0.0F, 0.0F, 15.0F));
          double angle = Math.random() * 2.0D * 3.141592653589793D;
          enemy.dx = ((float)Math.cos(angle) * 0.76F);
          enemy.dy = ((float)Math.sin(angle) * 0.76F);
          this.movables.add(enemy);
        }
        if (objDetails[0].equals("B")) {
          this.movables.add(new Barrel(Integer.parseInt(objDetails[1]), Integer.parseInt(objDetails[2]), new Circle(0.0F, 0.0F, 20.0F)));
        }
      }

    }

    for (int i = 0; i < this.outer.getPointCount() - 1; i++) {
      this.lines.add(new Line(this.outer.getPoint(i)[0], this.outer.getPoint(i)[1], this.outer.getPoint(i + 1)[0], this.outer.getPoint(i + 1)[1]));
      this.lines.add(new Line(this.outer.getPoint(this.outer.getPointCount() - 1)[0], this.outer.getPoint(this.outer.getPointCount() - 1)[1], this.outer.getPoint(0)[0], this.outer.getPoint(0)[1]));
    }
    Polygon p;
    int i;
    for (String obj = this.inners.iterator(); obj.hasNext(); 
      i < p.getPointCount() - 1)
    {
      p = (Polygon)obj.next();
      i = 0; continue;
      this.lines.add(new Line(p.getPoint(i + 1)[0], p.getPoint(i + 1)[1], p.getPoint(i)[0], p.getPoint(i)[1]));
      this.lines.add(new Line(p.getPoint(0)[0], p.getPoint(0)[1], p.getPoint(p.getPointCount() - 1)[0], p.getPoint(p.getPointCount() - 1)[1]));

      i++;
    }

    this.camX = (this.player.x - 400.0F);
    this.camY = (this.player.y - 300.0F);
  }

  public void update(GameContainer container, StateBasedGame game, int delta)
  {
    if (this.quality != 2) {
      this.particleSystem.update(delta);
    }

    this.deltaCount += delta;
    this.animationCounter += delta;

    float lastPX = this.player.x;
    float lastPY = this.player.y;

    this.player.dx = 0.0F;
    this.player.dy = 0.0F;

    if ((!this.won) && (!this.playerDead) && (!this.titleScreen))
    {
      if ((this.keys['Ë'] != 0) || (this.keys[30] != 0)) {
        this.player.dx = -1.1F;
      }

      if ((this.keys['Í'] != 0) || (this.keys[32] != 0)) {
        this.player.dx = 1.1F;
      }

      if ((this.keys['È'] != 0) || (this.keys[17] != 0)) {
        this.player.dy = -1.1F;
      }

      if ((this.keys['Ð'] != 0) || (this.keys[31] != 0)) {
        this.player.dy = 1.1F;
      }

      ArrayList removeList = new ArrayList();
      ArrayList addList = new ArrayList();

      for (Projectile p : this.projectiles)
        if ((Math.abs(p.x - this.player.x) > 700.0F) || (Math.abs(p.y - this.player.y) > 700.0F)) {
          removeList.add(p);
        }
        else {
          p.x += p.dx * delta / 1.0F;
          p.y += p.dy * delta / 1.0F;
          for (Line line : this.lines) {
            if (p.getShape().intersects(line)) {
              if (this.soundEnabled)
                this.soundHit.play(1.0F, 0.3F);
              removeList.add(p);
              break;
            }
          }
          for (Movable m : this.movables)
            if ((m instanceof Enemy)) {
              if ((p.getShape().intersects(m.getShape())) && (!p.hostile)) {
                if (this.soundEnabled)
                  this.soundHit2.play(1.0F, 0.3F);
                removeList.add(p);
                boolean dead = ((Enemy)m).hurt(10);
                if (dead) {
                  removeList.add(m);
                  this.kills += 1;
                  double rand = Math.random();
                  if (rand < 0.15D)
                    addList.add(new Medpack(m.x, m.y, new Circle(0.0F, 0.0F, 20.0F)));
                  else if (rand < 0.3D) {
                    addList.add(new Ammo(m.x, m.y, new Circle(0.0F, 0.0F, 20.0F)));
                  }
                }
                ConfigurableEmitter emitter = getEmitter(1);
                emitter.setPosition(m.x, m.y);
                emitter.replay();
                emitter.setEnabled(true);
                this.particleSystem.addEmitter(emitter);
              }
            }
            else if ((m instanceof Barrel)) {
              if (p.getShape().intersects(m.getShape())) {
                if (this.soundEnabled)
                  this.soundHit.play(1.0F, 0.3F);
                removeList.add(p);
                boolean dead = ((Barrel)m).hurt(10);
                if (dead) {
                  if (this.soundEnabled)
                    this.soundExplosion.play(1.0F, 0.5F);
                  removeList.add(m);
                  ConfigurableEmitter emitter = getEmitter(2);
                  emitter.setPosition(m.x, m.y);
                  emitter.replay();
                  emitter.setEnabled(true);
                  this.particleSystem.addEmitter(emitter);
                  for (Movable m2 : this.movables)
                    if (m2.distance(m) < 100.0F) {
                      if ((m2 instanceof Enemy)) {
                        dead = ((Enemy)m2).hurt(100);
                        if (dead) {
                          removeList.add(m2);
                          this.kills += 1;
                          double rand = Math.random();
                          if (rand < 0.15D)
                            addList.add(new Medpack(m.x, m.y, new Circle(0.0F, 0.0F, 20.0F)));
                          else if (rand < 0.3D) {
                            addList.add(new Ammo(m.x, m.y, new Circle(0.0F, 0.0F, 20.0F)));
                          }
                        }
                      }
                      if ((!(m2 instanceof Enemy)) && (!(m2 instanceof Barrel))) {
                        this.hp -= 100;
                        if (this.hp <= 0) {
                          this.hp = 0;
                          removeList.add(this.player);
                          this.playerDead = true;
                        }
                      }
                    }
                }
              }
            }
            else if (!(m instanceof Medpack))
            {
              if ((p.getShape().intersects(m.getShape())) && (p.hostile)) {
                if (this.soundEnabled)
                  this.soundHit2.play(1.0F, 0.3F);
                removeList.add(p);
                this.hp -= (int)(Math.random() * 3.0D) + 9;
                if (this.hp <= 0) {
                  removeList.add(this.player);
                  this.playerDead = true;
                }
              }
            }
        }
      double angle;
      for (??? = this.movables.iterator(); ???.hasNext(); 
        angle.hasNext())
      {
        Movable m = (Movable)???.next();

        m.x += m.dx * delta / 5.0F;
        m.y += m.dy * delta / 5.0F;

        float distance = m.distance(this.player);

        if ((m instanceof Enemy))
        {
          ((Enemy)m).deltaCount += delta;

          if ((distance < 300.0F) && (((Enemy)m).deltaCount >= 800)) {
            if (this.soundEnabled)
              this.soundShoot.play(1.0F, 0.3F);
            Projectile projectile = new Projectile(m.x, m.y, new Circle(0.0F, 0.0F, 4.0F), true);
            angle = Math.atan2(m.y - this.player.y, m.x - this.player.x);
            projectile.dx = ((float)-Math.cos(angle));
            projectile.dy = ((float)-Math.sin(angle));
            this.projectiles.add(projectile);
            ((Enemy)m).deltaCount = 0;
          }

          if ((distance < 250.0F) && (distance > 150.0F)) {
            double angle = Math.atan2(m.y - this.player.y, m.x - this.player.x);
            m.dx = ((float)-Math.cos(angle) * 0.76F);
            m.dy = ((float)-Math.sin(angle) * 0.76F);
          }

          for (Movable m2 : this.movables)
            if (((m2 instanceof Enemy)) && (m2.getShape().intersects(m.getShape()))) {
              double angle = Math.atan2(m.y - m2.y, m.x - m2.x);
              float pushX = (float)Math.cos(angle) * 0.1F;
              float pushY = (float)Math.sin(angle) * 0.1F;

              if (this.outer.contains(m.x + pushX, m.y + pushY)) {
                m.x += pushX;
                m.y += pushY;
              }
            }
        }
        else if ((m instanceof Medpack)) {
          if (m.distance(this.player) < 20.0F) {
            removeList.add(m);
            this.hp += 30;
            this.soundPickup.play(1.0F, 0.3F);
          }
        } else if ((m instanceof Ammo)) {
          if (m.distance(this.player) < 20.0F) {
            removeList.add(m);
            this.shotgunAmmo += 6;
            this.soundPickup.play(1.0F, 0.3F);
          }
        } else if ((!(m instanceof Enemy)) && (!(m instanceof Barrel)) && (!(m instanceof Medpack)) && (!(m instanceof Ammo))) {
          for (Movable m2 : this.movables) {
            if (m2 != m)
            {
              if ((!(m2 instanceof Medpack)) && (!(m2 instanceof Ammo)) && (m2.getShape().intersects(m.getShape()))) {
                double angle = Math.atan2(m.y - m2.y, m.x - m2.x);
                float pushX = (float)Math.cos(angle) * 5.0F;
                float pushY = (float)Math.sin(angle) * 5.0F;
                m.x += pushX;
                m.y += pushY;
              }
            }
          }
        }
        angle = this.lines.iterator(); continue; Line line = (Line)angle.next();
        if (m.getShape().intersects(line))
        {
          m.x -= m.dx * delta / 5.0F;
          m.y -= m.dy * delta / 5.0F;

          float ldx = line.getX1() - line.getX2();
          if (Math.abs(ldx) > 0.1D) {
            ldx /= Math.abs(ldx);
          }
          float ldy = line.getY1() - line.getY2();
          if (Math.abs(ldy) > 0.1D) {
            ldy /= Math.abs(ldy);
          }

          float ndx = ldy;
          float ndy = -ldx;

          float dot = ndx * m.dx + ndy * m.dy;

          m.x += ndx * 0.31F;
          m.y += ndy * 0.31F;

          m.dx -= ndx * dot;
          m.dy -= ndy * dot;

          m.x += m.dx * 0.3F;
          m.y += m.dy * 0.3F;
        }

      }

      for (Movable m : removeList) {
        if ((m instanceof Projectile)) {
          ConfigurableEmitter emitter = getEmitter(0);
          emitter.setPosition(m.x, m.y);
          emitter.replay();
          emitter.setEnabled(true);
          this.particleSystem.addEmitter(emitter);
        }
        this.projectiles.remove(m);
        this.movables.remove(m);
      }
      for (Movable m : addList) {
        this.movables.add(m);
      }
      if (this.keys[19] != 0) {
        this.ammo = 0;
        this.reloadTimer = 1500;
      }

      if (this.reloadTimer > 0) {
        this.reloadTimer -= delta;
        if (this.reloadTimer <= 0) {
          this.ammo = 20;
        }
      }
      this.shootTimer += delta;
      if (this.mouse[0] != 0) {
        shoot(this.mouseX, this.mouseY, false);
      }
      if (this.mouse[1] != 0) {
        shoot(this.mouseX, this.mouseY, true);
      }

      this.hpTimer += delta;

      if ((this.hpTimer > 1000) && (this.hp > 100)) {
        this.hp -= 1;
        this.hpTimer = 0;
      }
    }

    if (this.keys[1] != 0) {
      System.exit(0);
    }

    this.camX += (this.player.x - this.camX - 400.0F) * 0.006F * delta / 1.0F;
    this.camY += (this.player.y - this.camY - 300.0F) * 0.006F * delta / 1.0F;

    if ((!this.won) && (Math.sqrt(Math.pow(this.player.x - this.goalX, 2.0D) + Math.pow(this.player.y - this.goalY, 2.0D)) < 40.0D)) {
      this.soundWin.play(1.0F, 0.3F);
      this.won = true;
      this.killsTotal += this.kills;
      this.lastLevelShotgunAmmo = this.shotgunAmmo;
    }

    if (this.animationCounter > 170) {
      this.animationStep = (!this.animationStep);
      this.animationCounter = 0;
    }

    if (container.getFPS() < 30)
      this.fpsDropped += delta;
  }

  public void render(GameContainer container, StateBasedGame game, Graphics g)
  {
    g.setAntiAlias(false);

    g.setColor(new Color(45, 35, 20));
    g.fillRect(0.0F, 0.0F, 800.0F, 600.0F);

    g.resetTransform();

    g.translate(-this.camX, -this.camY);

    g.setColor(new Color(225, 210, 160));

    g.texture(this.outer, this.groundTexture, 0.000976563F, 0.000976563F);

    g.setAntiAlias(true);

    g.setColor(Color.black);
    g.setLineWidth(4.0F);
    g.draw(this.outer);

    for (Polygon p : this.inners) {
      g.setColor(new Color(45, 35, 20));
      g.fill(p);
      g.setColor(Color.black);
      g.draw(p);
    }

    if ((!this.playerDead) && (!this.titleScreen) && (!this.won)) {
      if (this.level == 0) {
        g.setColor(new Color(200, 180, 100, 150));
        g.setFont(this.buttonFont);
        g.setAntiAlias(false);
        g.drawString("There are two enemies by the exit.", 280.0F, 1290.0F);
        g.drawString("Try to defeat them!", 360.0F, 1320.0F);
        g.drawString("Exploding barrels", 1170.0F, 1062.0F);
        g.drawString("are deadly.", 1210.0F, 1092.0F);
        g.drawString("Press R to do a manual reload.", 1150.0F, 420.0F);
        g.drawString("Right click to fire your shotgun", 1150.0F, 480.0F);
        g.drawString("(if you've found ammo for it!).", 1150.0F, 510.0F);
        g.setAntiAlias(true);
      }if (this.level == 1) {
        g.setColor(new Color(200, 180, 100, 150));
        g.setFont(this.buttonFont);
        g.setAntiAlias(false);

        g.drawString("Explore the cave!", 1100.0F, 700.0F);
        g.setAntiAlias(true);
      }

    }

    g.setLineWidth(2.0F);

    g.setColor(new Color(150, 200, 100, 100));
    g.drawOval(this.goalX - 40.0F, this.goalY - 40.0F, 80.0F, 80.0F);
    g.drawOval(this.goalX - 36.0F, this.goalY - 36.0F, 72.0F, 72.0F);
    g.drawLine(this.goalX, this.goalY - 22.0F, this.goalX, this.goalY + 22.0F);
    g.drawLine(this.goalX - 22.0F, this.goalY, this.goalX + 22.0F, this.goalY);

    g.setLineWidth(1.0F);
    g.setColor(new Color(200, 200, 150));
    for (Projectile p : this.projectiles) {
      if ((Math.abs(p.x - this.player.x) <= 500.0F) && (Math.abs(p.y - this.player.y) <= 400.0F))
      {
        float angle = (float)Math.toDegrees(Math.atan2(p.dy, p.dx));
        g.rotate(p.x, p.y, angle);
        g.drawImage(this.bullet, p.x, p.y);
        g.rotate(p.x, p.y, -angle);
      }
    }

    for (Movable m : this.movables) {
      if ((Math.abs(m.x - this.player.x) <= 500.0F) && (Math.abs(m.y - this.player.y) <= 400.0F))
      {
        if ((m instanceof Enemy)) {
          float angle = (float)Math.toDegrees(Math.atan2(m.dy, m.dx)) + 90.0F;
          float angle2 = (float)Math.toDegrees(Math.atan2(m.y - this.player.y, m.x - this.player.x)) - 90.0F;
          if (m.distance(this.player) > 300.0F)
            g.rotate(m.x, m.y, angle);
          else {
            g.rotate(m.x, m.y, angle2);
          }

          if ((this.won) || (this.playerDead) || (this.titleScreen)) {
            g.drawImage(this.enemyBottom, m.x - m.getShape().getWidth() / 2.0F, m.y - m.getShape().getHeight() / 2.0F);
          }
          else if (this.animationStep)
            g.drawImage(this.enemyBottom, m.x - m.getShape().getWidth() / 2.0F, m.y - m.getShape().getHeight() / 2.0F);
          else {
            g.drawImage(this.enemyBottomFlipped, m.x - m.getShape().getWidth() / 2.0F, m.y - m.getShape().getHeight() / 2.0F);
          }

          g.drawImage(this.enemyTop, m.x - m.getShape().getWidth() / 2.0F, m.y - m.getShape().getHeight() / 2.0F);
          if (m.distance(this.player) > 300.0F)
            g.rotate(m.x, m.y, -angle);
          else
            g.rotate(m.x, m.y, -angle2);
        }
        else if ((m instanceof Barrel)) {
          g.drawImage(this.barrel, m.x - this.barrel.getWidth() / 2, m.y - this.barrel.getHeight() / 2);
        } else if ((m instanceof Medpack)) {
          g.drawImage(this.medpack, m.x - this.medpack.getWidth() / 2, m.y - this.medpack.getHeight() / 2);
        } else if ((m instanceof Ammo)) {
          g.drawImage(this.extraAmmo, m.x - this.extraAmmo.getWidth() / 2, m.y - this.extraAmmo.getHeight() / 2);
        } else {
          float angle = (float)Math.toDegrees(Math.atan2(m.dy, m.dx)) + 90.0F;
          float angle2 = (float)Math.toDegrees(Math.atan2(this.player.y - (this.camY + this.mouseY), this.player.x - (this.camX + this.mouseX))) - 90.0F;
          g.rotate(m.x, m.y, angle2);

          if (((m.dx == 0.0F) && (m.dy == 0.0F)) || (this.won) || (this.playerDead) || (this.titleScreen)) {
            g.drawImage(this.playerBottom, m.x - m.getShape().getWidth() / 2.0F, m.y - m.getShape().getHeight() / 2.0F);
          }
          else if (this.animationStep)
            g.drawImage(this.playerBottom, m.x - m.getShape().getWidth() / 2.0F, m.y - m.getShape().getHeight() / 2.0F);
          else {
            g.drawImage(this.playerBottomFlipped, m.x - m.getShape().getWidth() / 2.0F, m.y - m.getShape().getHeight() / 2.0F);
          }

          g.drawImage(this.playerTop, m.x - m.getShape().getWidth() / 2.0F, m.y - m.getShape().getHeight() / 2.0F);
          g.rotate(m.x, m.y, -angle2);
        }

      }

    }

    if (this.quality != 2) {
      this.particleSystem.render();
    }
    g.translate(this.camX, this.camY);

    g.setAntiAlias(false);
    g.setColor(new Color(120, 100, 50, 100));
    g.fillRoundRect(703.0F, 528.0F, 89.0F, 65.0F, 12);
    g.fillRoundRect(635.0F, 558.0F, 59.0F, 35.0F, 6);
    g.fillRoundRect(655.0F, 529.0F, 39.0F, 24.0F, 6);
    if ((this.won) || (this.playerDead) || (this.titleScreen)) {
      g.setColor(new Color(30, 20, 10, 220));
      g.fillRoundRect(50.0F, 50.0F, 700.0F, 450.0F, 24);
      Rectangle r = new Rectangle(315.0F, 400.0F, 160.0F, 50.0F);
      if (r.contains(this.mouseX, this.mouseY))
        g.setColor(new Color(70, 50, 30, 220));
      else {
        g.setColor(new Color(10, 10, 5, 220));
      }
      g.fillRoundRect(315.0F, 400.0F, 160.0F, 50.0F, 12);
    }
    g.setAntiAlias(true);
    g.setColor(new Color(200, 180, 100, 100));
    g.drawRoundRect(703.0F, 528.0F, 89.0F, 65.0F, 12);
    g.drawRoundRect(635.0F, 558.0F, 59.0F, 35.0F, 6);
    g.drawRoundRect(655.0F, 529.0F, 39.0F, 24.0F, 6);
    if ((this.won) || (this.playerDead) || (this.titleScreen))
    {
      g.drawRoundRect(50.0F, 50.0F, 700.0F, 450.0F, 24);
      g.drawRoundRect(315.0F, 400.0F, 160.0F, 50.0F, 12);
    }

    g.setColor(new Color(200, 180, 100));
    g.setAntiAlias(false);
    if (this.ammo > 0)
      g.fillRoundRect(638.0F, 562.0F, 53 * this.ammo / 20, 28.0F, 4);
    else {
      g.fillRoundRect(638.0F, 562.0F, 53 * (1500 - this.reloadTimer) / 1500, 28.0F, 4);
    }
    g.setAntiAlias(true);

    g.setFont(this.hpFont);
    g.drawString(this.hp, 746 - this.hpFont.getWidth(this.hp) / 2, 532.0F);

    g.setFont(this.messageFont);

    if (this.playerDead) {
      String message = "DEFEAT!";
      g.drawString(message, 400 - this.messageFont.getWidth(message) / 2 - 10, 190.0F);
    }
    int statsYoff = 0;
    if (this.won)
    {
      String message;
      if (this.level == levels.length - 1) {
        String message = "VICTORY!";
        statsYoff = -40;
      } else {
        message = "LEVEL COMPLETE!";
      }
      g.drawString(message, 400 - this.messageFont.getWidth(message) / 2 - 10, 190 + statsYoff);
    }
    if (this.titleScreen) {
      g.drawImage(this.logo, 400 - this.logo.getWidth() / 2 - 10, 90.0F);
    }
    g.setFont(this.smallFont);
    if (this.playerDead) {
      String message = "You are dead. Click below to restart the level.";
      g.drawString(message, 400 - this.smallFont.getWidth(message) / 2 - 10, 260.0F);
    }
    if (this.won)
    {
      String message;
      if (this.level == levels.length - 1)
        message = "You have fully explored the cave system!";
      else {
        message = "Congratulations! Click to proceed to level " + ((this.level + 1) % levels.length + 1) + ".";
      }
      g.setFont(this.smallFont);
      g.drawString(message, 400 - this.smallFont.getWidth(message) / 2 - 10, 260 + statsYoff);

      if (this.level == levels.length - 1)
        statsYoff = -15;
      else {
        statsYoff = 0;
      }
      String message = "Enemies killed: ";
      g.drawString(message, 400 - this.smallFont.getWidth(message) / 2 - 10 - 8, 325 + statsYoff);
      g.setFont(this.buttonFont);
      g.drawString(this.kills, 400 + this.smallFont.getWidth(message) / 2 - 15, 318 + statsYoff);

      if (this.level == levels.length - 1) {
        g.setFont(this.smallFont);
        message = "Kills total: ";
        g.drawString(message, 400 - this.smallFont.getWidth(message) / 2 - 10 - 8, 340.0F);
        g.setFont(this.buttonFont);
        g.drawString(this.killsTotal, 400 + this.smallFont.getWidth(message) / 2 - 15, 333.0F);
      }
    }

    if (this.titleScreen) {
      String message = "Walk and aim with WASD and mouse.";
      g.drawString(message, 400 - this.smallFont.getWidth(message) / 2 - 10, 296.0F);
      message = "[Q] toggles the three particle settings.";
      g.drawString(message, 400 - this.smallFont.getWidth(message) / 2 - 10, 316.0F);
      message = "Click below to begin your journey!";
      g.drawString(message, 400 - this.smallFont.getWidth(message) / 2 - 10, 336.0F);
      g.drawString("Made by Morre for Ludum Dare 15", 10.0F, 570.0F);
    }
    g.setFont(this.buttonFont);
    String message = this.shotgunAmmo;
    g.drawString(message, 675 - this.buttonFont.getWidth(message) / 2, 527.0F);
    if (this.playerDead) {
      message = "Restart";
      g.drawString(message, 400 - this.buttonFont.getWidth(message) / 2 - 7, 410.0F);
    }
    if (this.won) {
      if (this.level == levels.length - 1)
        message = "Main menu";
      else {
        message = "Continue";
      }
      g.drawString(message, 400 - this.buttonFont.getWidth(message) / 2 - 7, 410.0F);
    }
    if (this.titleScreen) {
      message = "Start!";
      g.drawString(message, 400 - this.buttonFont.getWidth(message) / 2 - 7, 410.0F);
    }

    g.drawImage(this.crosshair, this.mouseX - this.crosshair.getWidth() / 2, this.mouseY - this.crosshair.getHeight() / 2);
  }

  public void mousePressed(int button, int x, int y)
  {
    Rectangle r = new Rectangle(315.0F, 400.0F, 160.0F, 50.0F);
    if ((r.contains(x, y)) && ((this.won) || (this.playerDead) || (this.titleScreen))) {
      if (this.playerDead)
        initLevel(this.level);
      if (this.titleScreen) {
        this.titleScreen = false;
      }
      if (this.won) {
        this.level = ((this.level + 1) % levels.length);
        if (this.level == 0) {
          this.titleScreen = true;
          this.killsTotal = 0;
          this.shotgunAmmo = 0;
          this.lastLevelShotgunAmmo = 0;
        }
        initLevel(this.level);
      }
    }
    else {
      super.mousePressed(button, x, y);

      if (button == 0) {
        shoot(x, y, false);
      }
      if (button == 1)
        shoot(x, y, true);
    }
  }

  public void shoot(int x, int y, boolean shotgun)
  {
    if ((!shotgun) && (this.ammo > 0) && (this.shootTimer > 120) && (!this.won) && (!this.playerDead)) {
      if (this.soundEnabled) {
        this.soundShoot.play(1.0F, 0.3F);
      }
      double angle1 = Math.atan2(this.player.y - (this.camY + this.mouseY), this.player.x - (this.camX + this.mouseX));
      float addX = (float)(10.0D * Math.cos(angle1 - 90.0D));
      float addY = (float)(10.0D * Math.sin(angle1 - 90.0D));

      double angle = Math.atan2(this.player.y + addY - (this.camY + this.mouseY), this.player.x + addX - (this.camX + this.mouseX));
      Projectile projectile = new Projectile(this.player.x + addX, this.player.y + addY, new Circle(0.0F, 0.0F, 5.0F), false);
      projectile.dx = ((float)-Math.cos(angle));
      projectile.dy = ((float)-Math.sin(angle));
      this.projectiles.add(projectile);

      this.shootTimer = 0;
      this.ammo -= 1;

      if (this.ammo == 0) {
        this.reloadTimer = 1500;
      }
    }
    if ((shotgun) && (this.shotgunAmmo > 0) && (this.shootTimer > 500) && (!this.won) && (!this.playerDead)) {
      if (this.soundEnabled) {
        this.soundShoot.play(1.0F, 0.3F);
      }
      double angle1 = Math.atan2(this.player.y - (this.camY + this.mouseY), this.player.x - (this.camX + this.mouseX));
      float addX = (float)(10.0D * Math.cos(angle1 - 90.0D));
      float addY = (float)(10.0D * Math.sin(angle1 - 90.0D));

      double angle = Math.atan2(this.player.y + addY - (this.camY + this.mouseY), this.player.x + addX - (this.camX + this.mouseX));
      for (int i = -2; i < 3; i++) {
        Projectile projectile = new Projectile(this.player.x + addX, this.player.y + addY, new Circle(0.0F, 0.0F, 5.0F), false);
        projectile.dx = ((float)(-(0.8D + Math.random() * 0.6D) * Math.cos(angle + 3.141592653589793D * i / 24.0D)));
        projectile.dy = ((float)(-(0.8D + Math.random() * 0.6D) * Math.sin(angle + 3.141592653589793D * i / 24.0D)));
        this.projectiles.add(projectile);
      }

      this.shootTimer = 0;
      this.shotgunAmmo -= 1;
    }
  }

  public void keyPressed(int key, char c)
  {
    super.keyPressed(key, c);
    if (key == 16) {
      this.quality = ((this.quality + 1) % 3);
      if (this.quality == 0) {
        this.particleSystem.setUsePoints(false);
      }
      if (this.quality == 1)
        this.particleSystem.setUsePoints(true);
    }
  }

  public void reset()
  {
  }

  public ConfigurableEmitter getEmitter(int type)
  {
    if (type == 0) {
      this.boomEmitterCount = ((this.boomEmitterCount + 1) % this.boom.length);
      return this.boom[this.boomEmitterCount];
    }
    if (type == 1) {
      this.bloodEmitterCount = ((this.bloodEmitterCount + 1) % this.blood.length);
      return this.blood[this.bloodEmitterCount];
    }
    if (type == 2) {
      this.barrelEmitterCount = ((this.barrelEmitterCount + 1) % this.barrelboom.length);
      return this.barrelboom[this.barrelEmitterCount];
    }
    return null;
  }
}