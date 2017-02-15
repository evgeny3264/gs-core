package com.l2je.extensions.ghosts;

import java.util.ArrayList;
import java.util.List;

import net.sf.l2j.commons.random.Rnd;
import net.sf.l2j.gameserver.datatables.MapRegionTable;
import net.sf.l2j.gameserver.model.L2World;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.network.serverpackets.CreatureSay;

/**
 * @author redist
 */
public class GhostsShouts implements Runnable
{
	private static class SingletonHolder
	{
		protected static final GhostsShouts _instance = new GhostsShouts();
	}
	
	public static GhostsShouts getInstance()
	{
		return SingletonHolder._instance;
		
	}
	
	private static final String[] MESSAGES =
	{		
		"!!Набор в клан  !!!!",
		"кому нада рек",
		"?",
		"Кто на эвент",
		"г",
		"РЕАЛЬНО ДАЕТ",
		"кто даст рек за 50к)?",
		"я",
		"kto tronet bydet imet delo somnoi!!!!!",
		":D",
		"ЫЧ",
		"a snami eto vobwe jopa!!!!!",
		"Спуститесь с небес на землю",
		"ПИЗДЕЦ! ПРИШЛИ НА АРЕНУ СТОИМ НА ВХОДЕ!=))",
		"NEKA priwla",
		"ХОЧУ В КЛАН",
		"ХОчу секса",
		"ludi ne trogaute please mobov",
		"KYDA BCE CO6uPAuYTCA?",
		"АЛО",
		"го на арену",
		"народ продам СТРАЙДЕРА !!!!!!!!",
		"почем",
		"ню-ню",
		"плз дайте бабосики)",
		"рек плиз",
		"даите рек плз",
		"есть ноблесы в городе?",
		"dai deneg pliz",
		" профа ?",
		"ok)",
		"не ресай",
		"адвокат",
		"чо засцал?",
		"убейте его",
		"меняю bloody",
		"вы на боссе?",
		"баран ты тупой изза тебяменя килл",
		"крыса паганая",
		"a 4e?",
		"ты что крысиш",
		"падло",
		"руки поперебиваю",
		"ох языкатый",
		"извини тогда",
		"продам щит Ноугреид недорого со встроеным акуменом и станом",
		"гг",
		"можеть го на боса",
		"ГО НА РЕЙДА НАРОД",
		"кто на реида",
		"делаите парти",
		"ОХ ПИДАРАСЫ",
		"только они и их вынесут и все ДВ и ЛОА",
		"ЗА МОЕГО ДРУГАНА ПИЗДА ВАМ БУДЕТ ЛОХИ БЛЯТЬ",
		"онанисты))",
		"черти =)",
		"ТОЛЬКО ШАРОЙ МОЖЕТЕ",
		"ПАДЛЫ",
		"есть кто в ЛОА?",
		"а без пати килять никак?",
		"или сил и мозгов не хватает",
		"ну рес",
		"Дам бафф за Рекомендацию у ГК",
		"Продаю маски и заколки",
		"НАБОР В КЛАН 78 +НОБЛ ПРИВАТ",
		"АЛЁ",
		"ХОЧУ ЭВЕНТ",
		"Что никто точится не хочет?",
		"а че на 12 а не на 11",
		"суууууууууууука",
		"Точится на Enchant WAR набери join ",
		"ЧмОООООО ты где?",
		"так покупай",
		"WTT рек на рек",
		"КТО НА TVT наберите в чат info нубы",
		"so eto?)",
		"есть кто с Авалона?:)",
		"НАБОР В КЛАН! есть жилающие?",
		"ПРОСТО)",
		"да че слова норм написать не можете?",
		"про100 )))",
		"а то шо не норм слова?",
		"зачем тебе КМ",
		"kto ho4e na pk?",
		"Кто хочет на валакаласа?ПРИВАТ!",
		"юморит",
		"А типа Старовер когда появится ?",
		"Война, бесконечная стрельба над головой",
		"не ленисб иди набей",
		"kto ho4et v klan?",
		"ага желающих нет",
		"dam pek za 20k",
		"plz",
		"otset plz",
		"nu kto nit",
		"пидар ебучия увижу урою на хуй",
		"а я тебя найду",
		"est GM v igre???",
		"Веталь ты чего?",
		"убью нахуй",
		"я отвечаю",
		"пидарас",
		"tyt bez prof",
		"аа ну тода спасибо..",
		"бафф",
		"эвент убей фортачкину продолжается",
		"меняю мажор сет",
		"а чо за ивент?",
		":D",
		"ЖВ",
		"дайте ноблеса плиз",
		"Меняю Драконик На Арку приват!",
		"помогите качнутся",
		"што",
		"да",
		"ti uge u zamka?",
		"у кого есть Дракон????",
		"продайте плиз Дракона!!!ПМ",
		"кто знает как удалить клан писать в пм",
		"бляяя сломал",
		"выменяю драк лук 16",
		"фу вмз лох",
		"Набор в клан с ноблов",
		"как получить Bloody",
		"НА РБ 70+ ИДИ дебил",
		"МНЕ",
		"МНЕ!",
		"иди на северные ворота",
		"мНЕ!",
		"ААА",
		"Выменяю ваш Драконик сет +7",
		"бафф ЕЕ за рек!!!",
		"Продам маленького Дракончика",
		"ГМ тут есть?",
		"ты де ушел?",
		"подойди к раме глянь кто пришел",
		"i bish tebe ne pomozhet!!!",
		"примите в клан",
		"RES",
		"в 3 окна с бишопм",
		"нуб",
		"где ты?",
		"это саша напиши в ПМ",
		"ты где андрей",
		"старик шас",
		"на Годдарде вы все так же упадете,как мой член на первом свидании",
		"гулять",
		"иди",
		"сервер класс",
		"нах",
		"нубасики ",
		"куль",
		"что, будешь дальше задрачивать на пвп?:D",
		"бугыгыг",
		"=)",
		"ОПАЧА!",
		"ты терпило поганое иди в школу учись с людьми говорить",
		"КУКУХА ЧМО",
		"пацаны а Серый ето кто?",
		"Ник, Никоса видел?:D мухахахах",
		"давай я 1 против вас 2",
		"Серый давай йхй?",
		"1х1",
		"RES PLEAS",
		"кого тут надо валить и кто свои?",
		"красавец угнись",
		"красава всех оторвал :)",
		"саден сам по себе чмо",
		"Ha yJIuu,y",
		"Ha4uHaeLLIb y6uBaTb",
		"Oo",
		"TbI JIOX",
		"чё с ним говорить",
		"ахахахаха",
		"ы",
		"Ha4UHaeLLIb MoJIuTbc9",
		"связей у него много",
		"ооо да",
		"я ору",
		"u npocuTb",
		"анальный отросток",
		"зоофил - албанец и импатент ужс нах ))))",
		"или ты тока и можешь",
		"я по бабушкам не ташусь",
		"заткнули беднягу",
		"А Я С ПИВОМ БЫЛ :)))",
		"ты",
		"мда",
		"realwiki urodi",
		"полные...",
		"СЦУКИ ДИЧИ!!!!",
		"ЯЯ тебя трахну",
		"А ТАК",
		"ВЫМЕНЯЮ ВАШУ АРКАНУ РОБУ",
		"xa nub PK",
		"ПОМЕНЯЮ +6 С АКУМЕНОМ",
		"ВЫМЕНЯЮ МУНСТОН ШАРДЫ",
		"Аладин урод ты где",
		"СУКА ИДИ НАХ СО СВОИМИ А ТОЧКАМИ ЗАСУНЬ ИХ СЕБЕ В ЖОПУ",
		"РЕКОМЕНД=НОБЛЕСС!",
		"powol nax",
		"ты где))",
		"подай голос",
		"кто нибуть рек",
		"ЕСЛИ ТЫ НЕЗНАЕШЬ ДЕ ПОКУПАТЬ ЗАТОЧКИ ТО убейся",
		"выменяю ваши шарды пм!",
		"бугага",
		"набор в клан,алли=ПМ=",
		"кому блес А точку?",
		"каму нуна Ы бижа Подвеска и 2 кольца +5 в ПМ",
		"20кк или рек",
		"V adene",
		"кто пати ",
		"кто делал квест на дракона??",
		"кто еще на Blood Offering сдавать?",
		"будет весело",
		"Танки ау поход на РБ",
		"PK",
		"Они как педи-ки )",
		"Девион",
		"не гони",
		"она ксатит где?",
		"щас подожди",
		"pls dayte baf",
		"yps",
		"Кому сульфур надо на нобл?",
		"рес плыз я с тобой пк буду",
		"ШО ТЫ ЫЫЫЫКАЕШЬ?",
		"ого",
		"них се)",
		"on govoritb neumeet",
		"Menyayu даггер +12",
		"minyayu puzo drakonik v PM",
		"КУЧА МАЛА!!!!!!",
		"+1",
		"))))))",
		")))))))))",
		"куплю за 1 адену",
		"Меняю дагер с +9 с крит!!!ПМ",
		"лох и кидала !",
		"куплю рейд бижу за вмз в приват",
		"ухаха",
		"рекйдбижу",
		"лучше всего баюма и все)",
		"неужели))",
		"а все остальное ересть",
		"я у двух уже видел такую но они 4итерюги не продают(",
		"МЕНЯЮ ВАЛХАЛУ(АКУМ)",
		"ровняй руки сучара",
		"sosi",
		"МЕНЯЮ МАЖОР СЕТ НА ДРАКОНИК СЕТ",
		"ТОТ КТО НЕ КАТАЛ НИКОГДА В ЖИЗНИ В Л2 И ГОВОРИТ ЧТО ЭТО АЦТОЙ-ТОТ СУЧГА ГАЛИМАЯ",
		"ТОТ КТО КАТАЛ И ГОВОРИТ, ЧТО ЭТО АЦТОЙ- ТОМУ 5 БАЛОВ",
		"тут оказуется практически одни нюбы!",
		"людии",
		"go m9so na straiderah",
		"на олимпиаде хилки мона пить?",
		"за реал:)",
		"лутше кофе",
		"нобл суб шмот...",
		"Меняю дагер Ы +9 с критик!!ПМ",
		"Меняю сет S бижы+6!ПМ!",
		"Ha6op v klan toko c sub class",
		"я знаю",
		"аркану",
		"акумен",
		"+3",
		"komy nada S bigha PISHUTE V PM",
		"ну что?",
		"согласен",
		"скандал",
		"аха",
		"продаю банки ЦП",
		"ГДЕ?КТОО?",
		"форумский идиот",
		"он же личность",
		"ты че еимется сосать хочеш иди давай мы ждем козлина!",
		"советую именно ево:)",
		"даите А стрел",
		"Купи",
		"тебе позорную медаль за убивание нупов",
		"терпи",
		"сам признался что ты нуб",
		")",
		"ты сука з а е б и е б а л о",
		"ТХ ищет в пати мага для убиения непокорных)))",
		"нету",
		"выменяю сом с акуме",
		"ты сом меняеь?",
		"с са?",
		"срочьно выменяю ваши шарды!",
		"da zatkni rotk yeban",
		"ты урод",
		"даваи в реале",
		"посмотрим кто ты ест",
		"лошара",
		"gde kypit SOE?",
		"кого я вижу",
		"пипец)))",
		"fyy",
		"продам или поменяю  S",
		"НАБОР В КЛАН С НОРМ ОНЛАЙН В ПМ!",
		"na 4to vi",
		"на шо?",
		"где тут койны выбивать?",
		"пвп с любым файтером",
		"ыы",
		"давай",
		"hi all",
		"ку",
		"est кто на эвент?",
		"да вы нубыхахаха)",
		"анди",
		"я сказал что больше трогать не буду никогда",
		"я знаю что это не ты!",
		"у кого есть Ы випон заточки?",
		"ВТТ драк +8 ПМ",
		"pret",
		"здрасте",
		"стоять сцуко нубило!!!стоййй!",
		"сча",
		"ишу клан",
		"люди среди вас есть нублы!!",
		"плиз очень надо",
		"го на АРЕНУ",
		"Оо",
		"куплю баф профета",
		"сними блок",
		"мне скучно",
		"mne",
		"кто может продать мою мамашу на панель",
		"КОМУ НА АРЕНУ??!!",
		":)",
		"DauTe Enchant Ы weapon",
		"pls",
		"CPo4Ho",
		"ыыыыыы",
		"Все",
		"сори",
		"кстати привет всем",
		"реально не хотел",
		"noka",
		"мы тут :))",
		"здрям",
		"что?",
		"))",
		"da da",
		"на 1 етот на другом ",
		"вася иди накуй",
		"ты балобол",
		"который делает сосо",
		"дурная",
		"botovod typou",
		"ты тупо безмозглый чмошник",
		"а только глотает КУЙ",
		"закрой ротельник сказал и слушай",
		"давай встретимся лох и ты мне расскажешь чтото?",
		"Cyka",
		"Го все на арену",
		"каму Ы лук  +5",
		"+DEX",
		"dex +",
		"угу",
		"net",
		"go",
		"У МЯ КОИНЫ",
		"какие надо?",
		"Выменяю МУНСТОНШАРДЫ или Хелфаер ОИЛ!!ПМ",
		"Жорик мля ты где?",
		"ты мне??",
		"кто терпило?",
		"на что",
		"унас вар с пивчеком не валите",
		"ресните",
		"24х7 заказной убиффцо",
		"в обшяк",
		"есть ктото с онета???",
		"ВЫШО уснули",
		"вы все ссыкуны",
		"го",
		"го сюда",
		"ggg",
		"ему будет ппц",
		"все вместе го",
		"rec",
		"зараза нудно нехто реков недаёт",
		"ыыы",
		"выменяю ваш IMPERIAL шлем+10",
		"cerf",
		"vsem privetik",
		"_)",
		"продайю банки Цп",
		"фюрер*",
		"забейте вам замка не видатьф",
		"гигиг)",
		"ОРУ",
		"ПАТСТУЛОМ",
		"алли вар?",
		"тоже вариант",
		"ы)",
		"щас дам",
		"Албанец под русского косит",
		"Кхезеф албанский аццкий ахтунг",
		"оыч",
		"yo people",
		"yo black brother!",
		"прет ШУША",
		"dazhe ne za mat a prosto za rugatelstwo na 60 min mena nakazali",
		"я уже такой 2 раза получал",
		"нет тут бан автоматически?",
		"Собераю пати на рейда кто хочет",
		"У ГК",
		"Бляяяя реснет кто?",
		"скрины шас сделаю жоские:)",
		"senk",
		"hto",
		"vi men9 na pk",
		"диаблоа ты помниш когдатто а в керте всех на пк!помниш!!!!",
		"na wto?",
		"otmeni soe",
		"powel",
		"nx",
		"СУКА  ПИЗДЕЦ ШОБ ",
		"я писать не могу",
		"IIudaPbI",
		"Hae6awy",
		"9I cyka Bce",
		"Hy cyka 6aHH IIPOWoJI",
		"кто знает где на эвент рег",
		"ресните кто нить плз!",
		"козлы в натуре",
		"хоть я его когда-то...",
		"трах-бабач и нет его:D",
		"а он на м9 в алли",
		"ВСЕМ ЛЕЖАТЬ РАБОТАЕТ ОМОН!!!",
		"ууу",
		"прет",
		"вi че гоните отмойте меня",
		"я про клан",
		"та клан там криво головый а не криворукий=)",
		"Усе я на нобл",
		"меня устраивает",
		"На ступенях нет",
		"все в офф )",
		"Морда в грязи в жопе ветка по земсле ползет разведка!",
		"=)",
		")))",
		"ато у мну на",
		"тут половина людей в S по-албански балакають.=)",
		"СРАНЬ БЛЯ",
		"чей просто павер",
		"bomj",
		"dajte rekov",
		"я",
		"ta ya emy krita dal na 7,k )",
		"я придлагаю пойти тебе нах",
		"руки на етом серве кривые руки токо у тебя,карапуза и Диверсанта",
		"ммда",
		"люди где суб класс брать?",
		"ггг",
		"а?:)",
		"и еще дрочить если если есть что",
		"слыш",
		"ты",
		"в садике=)",
		"вау!!! НПС!",
		"сегодня праздник?)",
		"TP poyavilis ?",
		" :D",
		"vovrema",
		"СПАСИБА ГМ",
		"та выходи",
		"По телефону)",
		"народ так что эо такое???где все????",
		"оо",
		"Звоните Сандру",
		"будь проклято то кофе, которое я выпил",
		"ему самый норм)(",
		"plz D crystal",
		"ЗАЕБАЛИ УЖЕ СКОКА МОНА",
		"все грачи даже невыходят из города",
		"мляяя,как я тебя понимаю",
		"дайте Д кри плз",
		"а мне тоже 16 такчто цыц",
		"Ток не критом",
		"podoidi ko mne",
		"меня ишите????",
		"нету",
		"begite sdes GK novii po9vils9",
		"А потмо будем мородерствывать)",
		"та тут народ страдает в прятки",
		"спорим ненайдете",
		"та да иши",
		"гном ждет тебя=)",
		"все пиздец",
		"донт лук хелло",
		"товарисчи ЫБАТЬ",
		"а прикиньте нпс небудит (((((",
		"вdayte pliz REK",
		"я веду",
		"такс",
		"куку",
		"начнем отсчет",
		"хлопай",
		"абасаца",
		"начнем пиздеж",
		"ыч",
		"ИМХО",
		"Для сексуальных потребностей",
		"люди а когда НПЦ появятся",
		"никогда",
		"дайте чат бана)",
		"Жора,шо ты мелишь",
		"какой набор",
		"гг",
		"в контру несмодеш играть )))",
		"Набор в клан",
		"что мы не покупали за реал",
		"знач мстят суки",
		"может они делают коины",
		"ЛОЛ",
		"aga",
		"и мстя их сильна",
		"кто мстит",
		"какого хуя убрали нсп",
		"ну-ну",
		"забейй",
		"идите бомжуйте",
		"iдi сюдi )",
		"эт куды))",
		"!!",
		"АГА)",
		"шо лох",
		"не базарь",
		"Куда ты пойдеш со своим пати?",
		"онуфрий!",
		"))",
		"анонс когда мобы будут????",
		"сука пидрилы меня не пиздить нужно а поддерживать",
		"Мировой мульт",
		"ага",
		"сами забойний",
		"вам че делать нехуй",
		"ебвть где все",
		"8)",
		"кому шо=))",
		"DA",
		"kto flydit ?",
		"ты",
		"у меня чото с головой )",
		"та харе флудить",
		"оно видно",
		"пизда флууд",
		"тишина",
		")",
		"ppc",
		"a de koti?",
		"берет в рот за 5р",
		"molodec ti mozg",
		"Тупые базары",
		"ti maloletka ebaniy zavali ebalo",
		"спасиюо за комплемент",
		"малолетка ты",
		"за пидой следи",
		"ti za slovami sledi psina",
		"na koncerte",
		"ktoto 4toto znaet?",
		"posle restarta vse is4ezli",
		"mraz",
		"поетому",
		"шлюха",
		"pidar",
		"axa",
		"vse mne neo4em s toboy razgovarivat",
		"бо ыты шлюха",
		"davay",
		"аты самая первая?",
		"ыч",
		"го в реале поговорим?",
		"Ебическая сила",
		"ебало завали СОСКА",
		"прива прива",
		"сука рот закрыла овечка тупая",
		"рек кто не пидр плизя почти у гк",
		"у человека чат банн",
		"на 3 буквы",
		"ВСЕМ ПРИВЕТИККК",
		"так что",
		"Лана",
		"Это",
		"народ кто то ест на арене",
		"Вступлю в гуд клан",
		"идемте все валицца",
		"только ))",
		"я последний",
		"я нихачу умирать!",
		"я так молод",
		"Сначало умру я",
		"Потом вы все",
		"ыыы",
		"продам радость",
		"в приват",
		"ахах",
		"Через год",
		"Сука",
		"он закрил душкой в гиране Гк",
		"вы де?",
		"а вижу",
		"СТОП",
		"сцуко",
		"опять начинаешь?",
		"Ностр од вы туд?",
		"пожалуйста",
		"г",
		"ну если МОЖНА ПО БЫСТРЕЕ",
		"а что с сервером",
		"blya nahyy blya nahyy",
		"4to na penciy yhol?",
		"poka gmov nety",
		"gde GK",
		"читай выше мои слова",
		"pidarasu",
		"ХЗ",
		"res",
		"osobe PKawut tex y kogo zna4ok klana soderjut bykvu PK in sam ne PK",
		"бб",
		"Вот ето порядок!",
		"баны в ход пошли",
		"иди сюда гнида",
		"нет",
		"квест",
		"пройди",
		"вопрос, а у кого менять дракона на страйдера??",
		"АДМИН ПРОЧТИ ЕМЕИЛ",
		"ага",
		"и тебя?",
		"перса основного",
		"ВОЗЛЕ ГК!",
		"врач приветт",
		"сцука я б тебе пизды дал в реале",
		"а ты че мент или че?",
		"sidi i ne gavkai",
		"стрелочник куев",
		"пнх",
		"samo powlo",
		"та он черт!",
		"Ишю клан ( Приват ) !",
		"С ними по другому нельзя",
		"сорри",
		"STOY",
		"ыыыыы педик)",
		"=)",
		"обменяю Бсое",
		"пидораст",
		"хуй посадите суки",
		"ебал я вас в рот!!!!!!!",
		"gg",
		"Народ, кто каую музыку слушает?",
		"это типа меня зовут Алисса и мне 18 а переводи как хочеш))",
		"че нельзя? тут всё можно",
		"седня гуляем",
		"))))",
		"bl4",
		"у меня нету денег",
		"(((",
		"Ы",
		"mne uje nesky4no =)",
		"мне скушно",
		"тыЧЕ!? О_о",
		"мне пиздец как весело",
		"мне тоже скучно и я пашол сПАТЬ",
		"A MIXALI4 ZAPRAVLAET MESTNIJ RAJON DA?",
		"Кто хочеть выйти замуж?",
		"И не спрашивай",
		"гегеге",
		"76",
		"))",
		"както ху   вато(",
		"40 бля",
		"я верю",
		"ДРУЖИТЬ БУДЕМ",
		"Народ,как мне хуёвооо,вы не поверите",
		"иди сюдаа",
		"как берешь квест в ивори",
		"в л2",
		"неа",
		"ny da ymnee 4to to npidymau",
		"народ скока тут хомка стоит?",
		"люди тут 1 профу ку котов моно сдать?",
		"kyply vumeHayu arcany",
		"menyu SA 9lvl ,krasnii na zat",
		"склей ебало",
		"ты куда то спешишь?)",
		"ингаааа приветтт",
		"давай втемпе",
		"ты еще дОЛГОООО",
		"идууу",
		"u wo 9 vam ne tak sdelal?",
		"кто ПКшит?",
		"de vz9t staryu adeny",
		"В катах набить камешков и обменять их",
		"a v kakix pliz skagi",
		"В любых",
		"ъ",
		"ne vezde est mobi:(",
		"VSE GO SYDA TYT PK",
		"ПК",
		"ГДЕ?",
		"та заткни ебало бивень",
		"долбаёб",
		"kaleka pisat nau4is9",
		"я маг давай пати",
		"ты где?",
		"в годарде",
		"у гк",
		"приходи к оркам",
		"ты где",
		"там в середине",
		"приходи нас уже 2",
		"ыч",
		"еще немного плз",
		"Выменяю ваш МАЖОР пузо приват",
		"я хотю",
		"а там телки есть",
		"и где будет дискотека?",
		"в Гиране",
		"надо чтоб дали впарить",
		"вторая",
		"да?",
		"темпелкнайт говорюже",
		"овер ишет клан..",
		"вандалы",
		"на могиле плясать",
		"гыы",
		"Не смешно",
		"а где именно",
		"у меня там Паладин застрял",
		"Какого хрена меня в Годдарде выкидывает",
		"Набор в клан (КланХолл)в ПМ 76+",
		"набор в клан, в пм мне !",
		"ПОСЛЕ 16 1 ЛВЛ ДАЛИ",
		"спс",
		"budet narod kone4no poidem!!!",
		"А УТКИ И ЧАЙКИ ПРИЛЕТЕЛИ",
		"ЗАДРОТЫ!!!!",
		":0",
		"lydi tyt yest xtota ?",
		"бухать го! го! го!",
		"Сука уберите сумона",
		"5 мин",
		")",
		"У вас есть возможность",
		"смыться из города",
		"стой там",
		"в катакомбах",
		"у мамона брать",
		"в гиране?",
		"kak i na drakowy",
		"бебе)",
		"я знаю )",
		"я такой )",
		"Ну суКа",
		")))",
		"hi all",
		"znaew 4to 9 tebe was v rot day))",
		"не ты не даешь ему)",
		"ему я даю )",
		"аа пидарочег ))",
		"все досвидания нубыыы",
		"удачи вам тут",
		"воть и сидите кучкой в 5 челоаек",
		"ну ты жжешь )",
		"Здам в оренду одноглазого змея (он не ядовитый не кусается токо плюется иногда) )))",
		"Герой бля",
		"ААААААААААААААААа фарт",
		"крита дал",
		"Мне рекомендацыю вам бафф ее",
		"Плз рекните! Буду благодарен!!!",
		"на что",
		"vimeanyu som s acum!",
		"PROSTO",
		"где?",
		"какой грейд?",
		"Б",
		"он умер",
		"ы",
		"фантомас что хочеш???",
		"NU?",
		"ja imel v vidu)",
		"дам канхвету за рек)",
		"давай ещё?",
		"davai",
		"1 сек",
		"oki",
		"тока что рестарт был?",
		"да",
		"люди а в годарде есть ГМ ШОП?",
		"понятна",
		"какая разница",
		"де заточки брать??",
		"в бабруйске",
		"люди",
		"тут таких нету",
		"menyay zato4ki!!!!!",
		"плиз бафф",
		"ЖЛОБИНА",
		"БАФА ЖАЛКО",
		"де стрели для д лука купить",
		"vi pripi3dki",
		"9 svoi",
		"какую тату на некра ???",
		"Куплю СТРАЙДЕРА СРОЧНО НО ОЧЕНЬ ДОРОГО!!",
		"у кого саб на некро??",
		"У меня",
		"а на какого вы рейда??",
		"люди!! где хорошую краску можно купить?",
		"самунёр хо в клан",
		"по чем краски?",
		"205кг пизды)",
		"сорь",
		"у кого есть 4 ветки?",
		"9i skajy",
		"4esno",
		"nudapac",
		"меняю  +10 ",
		"люди ва чего ападло помоч?",
		// "ПРОДАМ ПЕТА ЗА 70 КОИНОВ",
		"))",
		"kakogo?",
		"птенец страйдера",
		"а шо там его проходить?",
		"у Пушкина",
		"у в гиране",
		"спс",
		"где точки продают???",
		"пасть прикрой",
		"возле гкПй всем миньты делает на халяву)))",
		"у всех подрд сосет",
		"gg was podoidy )",
		"EBAT!",
		"Леночка ты тут?",
		"lol",
		// "БЛИН НАРОД ЗАВАЛИТЕ ПК В дв !))",
		"Воробушек ты де?",
		"ktonit poka4ayte mnya",
		"plis",
		"ыч",
		"видимость прекрасная",
		 "wa vsex v pk",
		 "go go go",
		// "ya ne lider",
		"Кто знает как называется квест на Тараса?",
		"сука фригидная",
		"да ты маленькая ещё",
		"люди скажите плз какие тату на П атаку",
		"плз",
		// schoolmemories
		"suxx",
		// "kyply d-grade palky pls, ato s ng dolgo ka4 v loa",
		// "ок, беру за 300к больше нету",
		// "kniga na +3(acum) za 20kk",
		"eto war polybe",
		// "macc chat!!!",
		"Вася з першого поверху ))",
		"жидкий метал?",
		// "vika, <3 murka",
		"xa-xa",
		// "ради орфгазма",
		"пвнуха )",
		"ппц!!!",
		// "go v sredy v cw ??",
		// "ka4 ili razvod?",
		// "baff 150k",
		// "go segodnya v cw ili v add",
		"esli xo4 mogew sam",
		// "Вафель!",
		// "chant of vika",
		// "WTT Вику",
		"я отказываюсь фоткаться трезвым-)",
		// "стоп, а гдеже вика???",
		"цоци..уй понял?",
		// "наеб 10кк",
		// "захват замка Do it!!!",
		"телега с овощами",
		"мангал",
		"все по книжке))",
		"калитка )",
		"мивина )))",
		"барбекю=)",
		"дичь :)",
		"алалал",
		// "это тупо там онлайн 2-3 чела",
		"откуда у тебя тёлка?",
		// "у неё нету сестры или подруги???:)",
		"посмотрим как ты это зделаеш",
		// "tak! mi go v add",
		// "go was i potom :)",
		"mne samomy nygen, takwo ,bb",
		"lana bb",
		"all hi",
		"как дела? куда го?",
		"kuplu Drakonik Bow sro4no (privat)",
		"men9u major robe na a wmot (privat)",
		"ебало твоё черное ))",
		// "яцик",
		// "go v add ili cw ?",
		"komu buff?:)",
		"sek 9 bd i swsa zavedu",
		// "go pvp pinokio",
		// "Надо понулить Danny (Do it !!!)",
		// "надо забить на х50!!!",
		"а шо тут одни варки?",
		"под фулом я буду бить стылу, а ты танковать",
		"4o segodn9 budem delat",
		// "ludi nu prodaite mne wmot!!!",
		"лох ниндзя",
		"слыш барига ты за базар ответиш???",
		"кислотные дожди )))",
		// "вад",
		// "Чат Bizzard регестрируемся!",
		// "DoTa v6.37:)",
		// "Traifel rulles",
		"борода)"
		// "МОНО спасает!!!",
		// "P.S.(исает!!!)"
	};
	
	// Врямя между криками Miliseconds
	private static final int[] SHOUTTIME =
	{		
		134451,
		265522,
		569010,
		161331,
		168595,
		198841,
		191243,
		395534,
		497440,
		651544,
		491544,
		320555,
		219999,
		314000,
		421000
		
	};
	
	public static int getShoutRndTime()
	{
		return SHOUTTIME[Rnd.get(SHOUTTIME.length)];
	}
	
	private static final int[] CHATS =
	{		
		1
	};
	private final static int INITIAL_DELAY = 30000; // Miliseconds
	public static int getIntialDelay()
	{
		return INITIAL_DELAY;
	}
	/*
	 * ALL = 0; SHOUT = 1; //! TELL = 2; PARTY = 3; //# CLAN = 4; //@ GM = 5; //gmchat PETITION_PLAYER = 6; // used for petition PETITION_GM = 7; //* used for petition TRADE = 8; //+ ALLIANCE = 9; //$ ANNOUNCEMENT = 10; //announce PARTYROOM_ALL = 16; //(Red) PARTYROOM_COMMANDER = 15; //(Yellow)
	 * HERO_VOICE = 17; //%
	 */
	@Override
	public void run()
	{
		try
		{
			CreatureSay cs = new CreatureSay(0, getRandomChat(), getRandomPlayer(), getRandomText());
			
			L2PcInstance player = null;
			player = getNewFake();
			if (player != null)
			{
				int region = MapRegionTable.getMapRegion(player.getX(), player.getY());
				
				for (L2PcInstance pc : L2World.getInstance().getAllPlayers())
				{
					if (region == MapRegionTable.getMapRegion(pc.getX(), pc.getY()) && !pc.isGhost())
					{
						pc.sendPacket(cs);
					}
				}
			}
		}
		catch (Throwable t)
		{
			t.printStackTrace();
		}
	}
	
	private static String getRandomPlayer()
	{
		return GhostsPlayers.getInstance().getRandomGhostName();
	}
	
	private static String getRandomText()
	{
		return MESSAGES[Rnd.get(MESSAGES.length)];
	}
	
	private static int getRandomChat()
	{
		return CHATS[Rnd.get(CHATS.length)];
	}
	
	private static L2PcInstance getNewFake()
	{
		List<L2PcInstance> players = new ArrayList<>(L2World.getInstance().getAllPlayers());
		if (players.isEmpty())
			return null;
		return players.get(Rnd.nextInt(players.size()));
	}
	
}
