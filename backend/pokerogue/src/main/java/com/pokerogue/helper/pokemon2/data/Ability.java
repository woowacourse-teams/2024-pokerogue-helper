package com.pokerogue.helper.pokemon2.data;


import java.util.Arrays;

public enum Ability {
    STENCH("Stench", "악취", "악취를 풍겨서 공격했을 때 상대가 풀죽을 때가 있다."),
    DRIZZLE("Drizzle", "잔비", "등장했을 때 날씨를 비로 만든다."),
    SPEEDBOOST("Speed Boost", "가속", "매 턴 스피드가 올라간다."),
    BATTLEARMOR("Battle Armor", "전투무장", "단단한 껍질에 보호받아 상대의 공격이 급소에 맞지 않는다."),
    STURDY("Sturdy", "옹골참", "상대 기술을 받아도 일격으로 쓰러지지 않는다. 일격필살 기술도 효과 없다."),
    DAMP("Damp", "습기", "주변을 습하게 함으로써 자폭 등 폭발하는 기술을 아무도 못 쓰게 한다."),
    LIMBER("Limber", "유연", "유연한 몸으로 인해 마비 상태가 되지 않는다."),
    SANDVEIL("Sand Veil", "모래숨기", "모래바람일 때 회피율이 올라간다."),
    STATIC("Static", "정전기", "정전기를 몸에 둘러 접촉한 상대를 마비시킬 때가 있다."),
    VOLTABSORB("Volt Absorb", "축전", "전기타입의 기술을 받으면 데미지를 받지 않고 회복한다."),
    WATERABSORB("Water Absorb", "저수", "물타입의 기술을 받으면 데미지를 받지 않고 회복한다."),
    OBLIVIOUS("Oblivious", "둔감", "둔감해서 헤롱헤롱이나 도발 상태가 되지 않는다."),
    CLOUDNINE("Cloud Nine", "날씨부정", "모든 날씨의 영향이 없어진다."),
    COMPOUNDEYES("Compound Eyes", "복안", "복안을 가지고 있어 기술의 명중률이 올라간다."),
    INSOMNIA("Insomnia", "불면", "잠들지 못하는 체질이라 잠듦 상태가 되지 않는다."),
    COLORCHANGE("Color Change", "변색", "상대에게 받은 기술의 타입으로 자신의 타입이 변화한다."),
    IMMUNITY("Immunity", "면역", "체내에 면역을 가지고 있어 독 상태가 되지 않는다."),
    FLASHFIRE("Flash Fire", "타오르는불꽃", "불꽃타입의 기술을 받으면 불꽃을 받아서 자신이 사용하는 불꽃타입의 기술이 강해진다."),
    SHIELDDUST("Shield Dust", "인분", "인분에 보호받아 기술의 추가 효과를 받지 않게 된다."),
    OWNTEMPO("Own Tempo", "마이페이스", "마이페이스라서 혼란 상태가 되지 않는다."),
    SUCTIONCUPS("Suction Cups", "흡반", "흡반으로 지면에 달라붙어 포켓몬을 교체시키는 기술이나 도구의 효과를 발휘하지 못하게 한다."),
    INTIMIDATE("Intimidate", "위협", "등장했을 때 위협해서 상대를 위축시켜 상대의 공격을 떨어뜨린다."),
    SHADOWTAG("Shadow Tag", "그림자밟기", "상대의 그림자를 밟아 도망치거나 교체할 수 없게 한다."),
    ROUGHSKIN("Rough Skin", "까칠한피부", "공격을 받았을 때 자신에게 접촉한 상대를 까칠까칠한 피부로 상처를 입힌다."),
    WONDERGUARD("Wonder Guard", "불가사의부적", "효과가 굉장한 기술만 맞는 불가사의한 힘."),
    LEVITATE("Levitate", "부유", "땅에서 뜨는 것으로 땅타입의 기술을 받지 않는다."),
    EFFECTSPORE("Effect Spore", "포자", "공격으로 자신에게 접촉한 상대를 독이나 마비, 잠듦 상태로 만들 때가 있다."),
    SYNCHRONIZE("Synchronize", "싱크로", "자신이 걸린 독이나 마비, 화상을 상대에게 옮긴다."),
    CLEARBODY("Clear Body", "클리어바디", "상대 기술이나 특성으로 능력이 떨어지지 않는다."),
    NATURALCURE("Natural Cure", "자연회복", "지닌 포켓몬으로 돌아오면 상태 이상이 회복된다."),
    LIGHTNINGROD("Lightning Rod", "피뢰침", "전기타입의 기술을 자신에게 끌어모아 데미지를 받지 않고 특수공격을 올린다."),
    SERENEGRACE("Serene Grace", "하늘의은총", "하늘의 은총 덕분에 기술의 추가 효과가 나오기 쉽다."),
    SWIFTSWIM("Swift Swim", "쓱쓱", "비가 오는 날씨일 때 스피드가 올라간다."),
    CHLOROPHYLL("Chlorophyll", "엽록소", "날씨가 맑을 때 스피드가 올라간다."),
    ILLUMINATE("Illuminate", "발광", "주변을 밝게 하여 명중률이 떨어지지 않는다."),
    TRACE("Trace", "트레이스", "등장했을 때 상대의 특성을 트레이스해서 같은 특성이 된다."),
    HUGEPOWER("Huge Power", "천하장사", "물리공격의 위력이 2배가 된다."),
    POISONPOINT("Poison Point", "독가시", "자신과 접촉한 상대를 독 상태로 만들 때가 있다."),
    INNERFOCUS("Inner Focus", "정신력", "단련한 정신으로 인하여 상대의 공격에 풀죽지 않는다."),
    MAGMAARMOR("Magma Armor", "마그마의무장", "뜨거운 마그마를 몸에 둘러서 얼음 상태가 되지 않는다."),
    WATERVEIL("Water Veil", "수의베일", "물의 베일을 몸에 둘러서 화상 상태가 되지 않는다."),
    MAGNETPULL("Magnet Pull", "자력", "강철타입의 포켓몬을 자력으로 끌어모아 도망칠 수 없게 한다."),
    SOUNDPROOF("Soundproof", "방음", "소리를 차단하는 것으로 소리 공격을 받지 않는다."),
    RAINDISH("Rain Dish", "젖은접시", "비가 오는 날씨일 때 조금씩 HP를 회복한다."),
    SANDSTREAM("Sand Stream", "모래날림", "등장했을 때 날씨를 모래바람으로 만든다."),
    PRESSURE("Pressure", "프레셔", "프레셔를 줘서 상대가 쓰는 기술의 PP를 많이 줄인다."),
    THICKFAT("Thick Fat", "두꺼운지방", "두꺼운 지방으로 보호되고 있어 불꽃타입과 얼음타입의 기술의 데미지를 반감시킨다."),
    EARLYBIRD("Early Bird", "일찍기상", "잠듦 상태가 되어도 2배 스피드로 깨어날 수 있다."),
    FLAMEBODY("Flame Body", "불꽃몸", "자신과 접촉한 상대를 화상 상태로 만들 때가 있다."),
    RUNAWAY("Run Away", "도주", "야생 포켓몬으로부터 반드시 도망칠 수 있다."),
    KEENEYE("Keen Eye", "날카로운눈", "날카로운 눈 덕분에 명중률이 떨어지지 않는다."),
    HYPERCUTTER("Hyper Cutter", "괴력집게", "힘이 자랑인 집게를 가지고 있어 상대가 공격을 떨어뜨리지 못한다."),
    PICKUP("Pickup", "픽업", "상대가 지닌 도구를 주워올 때가 있다."),
    TRUANT("Truant", "게으름", "기술을 사용하면 다음 턴은 쉰다."),
    HUSTLE("Hustle", "의욕", "자신의 공격이 높아지지만 명중률이 떨어진다."),
    CUTECHARM("Cute Charm", "헤롱헤롱바디", "자신과 접촉한 상대를 헤롱헤롱 상태로 만들 때가 있다."),
    PLUS("Plus", "플러스", "플러스나 마이너스의 특성을 가진 포켓몬이 동료에 있으면 자신의 특수공격이 올라간다."),
    MINUS("Minus", "마이너스", "플러스나 마이너스의 특성을 가진 포켓몬이 동료에 있으면 자신의 특수공격이 올라간다."),
    FORECAST("Forecast", "기분파", "날씨의 영향을 받아 물타입, 불꽃타입, 얼음타입 중 하나로 변화한다."),
    STICKYHOLD("Sticky Hold", "점착", "점착질의 몸에 도구가 달라붙어 있어 상대에게 도구를 뺏기지 않는다."),
    SHEDSKIN("Shed Skin", "탈피", "몸의 껍질을 벗어 던져 상태 이상을 회복할 때가 있다."),
    GUTS("Guts", "근성", "상태 이상이 되면 근성을 보여서 공격이 올라간다."),
    MARVELSCALE("Marvel Scale", "이상한비늘", "상태 이상이 되면 이상한 비늘이 반응해서 방어가 올라간다."),
    LIQUIDOOZE("Liquid Ooze", "해감액", "해감액을 흡수한 상대는 강렬한 악취로 데미지를 받아 HP가 줄어든다."),
    OVERGROW("Overgrow", "심록", "HP가 줄었을 때 풀타입 기술의 위력이 올라간다."),
    BLAZE("Blaze", "맹화", "HP가 줄었을 때 불꽃타입 기술의 위력이 올라간다."),
    TORRENT("Torrent", "급류", "HP가 줄었을 때 물타입 기술의 위력이 올라간다."),
    SWARM("Swarm", "벌레의알림", "HP가 줄었을 때 벌레타입 기술의 위력이 올라간다."),
    ROCKHEAD("Rock Head", "돌머리", "반동을 받는 기술을 사용해도 HP가 줄지 않는다."),
    DROUGHT("Drought", "가뭄", "등장했을 때 날씨를 맑음으로 만든다."),
    ARENATRAP("Arena Trap", "개미지옥", "배틀에서 상대를 도망칠 수 없게 한다."),
    VITALSPIRIT("Vital Spirit", "의기양양", "의기양양해져서 잠듦 상태가 되지 않는다."),
    WHITESMOKE("White Smoke", "하얀연기", "하얀 연기의 보호를 받아 상대가 능력을 떨어뜨릴 수 없다."),
    PUREPOWER("Pure Power", "순수한힘", "요가의 힘으로 물리공격의 위력이 2배가 된다."),
    SHELLARMOR("Shell Armor", "조가비갑옷", "단단한 껍질의 보호를 받아 상대의 공격이 급소에 맞지 않는다."),
    AIRLOCK("Air Lock", "에어록", "모든 날씨의 영향이 없어진다."),
    TANGLEDFEET("Tangled Feet", "갈지자걸음", "혼란 상태일 때는 회피율이 올라간다."),
    MOTORDRIVE("Motor Drive", "전기엔진", "전기타입의 기술을 받으면 데미지를 받지 않고 스피드가 올라간다."),
    RIVALRY("Rivalry", "투쟁심", "성별이 같으면 투쟁심을 불태워 강해진다. 성별이 다르면 약해진다."),
    STEADFAST("Steadfast", "불굴의마음", "풀죽을 때마다 불굴의 마음을 불태워 스피드가 올라간다."),
    SNOWCLOAK("Snow Cloak", "눈숨기", "날씨가 눈일 때 회피율이 올라간다."),
    GLUTTONY("Gluttony", "먹보", "HP가 줄어들면 먹을 나무열매를 HP가 절반일 때 먹어버린다."),
    ANGERPOINT("Anger Point", "분노의경혈", "급소에 공격이 맞으면 크게 분노해 공격력이 최대가 된다."),
    UNBURDEN("Unburden", "곡예", "지니던 도구가 없어지면 스피드가 올라간다."),
    HEATPROOF("Heatproof", "내열", "내열인 몸으로 인해 불꽃타입 공격의 데미지를 반감한다."),
    SIMPLE("Simple", "단순", "능력 변화가 평소의 2배가 된다."),
    DRYSKIN("Dry Skin", "건조피부", "비가 오는 날씨나 물타입의 기술로 HP가 회복되고 맑을 때나 불꽃타입의 기술로는 줄어든다."),
    DOWNLOAD("Download", "다운로드", "상대의 방어와 특수방어를 비교해서 낮은 쪽 능력에 맞춰서 자신의 공격이나 특수공격을 올린다."),
    IRONFIST("Iron Fist", "철주먹", "펀치를 사용하는 기술의 위력이 올라간다."),
    POISONHEAL("Poison Heal", "포이즌힐", "독 상태가 되면 HP가 줄지 않고 증가한다."),
    ADAPTABILITY("Adaptability", "적응력", "자신과 같은 타입의 기술 위력이 올라간다."),
    SKILLLINK("Skill Link", "스킬링크", "연속 기술을 사용하면 항상 최고 횟수를 사용할 수 있다."),
    HYDRATION("Hydration", "촉촉바디", "비가 오는 날씨일 때 상태 이상이 회복된다."),
    SOLARPOWER("Solar Power", "선파워", "날씨가 맑으면 특수공격이 올라가지만 매 턴 HP가 줄어든다."),
    QUICKFEET("Quick Feet", "속보", "상태 이상이 되면 스피드가 올라간다."),
    NORMALIZE("Normalize", "노말스킨", "어떤 타입의 기술도 모두 노말타입이 된다. 위력이 조금 올라간다."),
    SNIPER("Sniper", "스나이퍼", "공격을 급소에 맞혔을 때 위력이 더욱 올라간다."),
    MAGICGUARD("Magic Guard", "매직가드", "공격 이외에는 데미지를 입지 않는다."),
    NOGUARD("No Guard", "노가드", "노가드전법에 따라 서로가 사용하는 기술이 반드시 맞게 된다."),
    STALL("Stall", "시간벌기", "기술을 사용하는 순서가 반드시 마지막이 된다."),
    TECHNICIAN("Technician", "테크니션", "위력이 약한 기술의 위력을 올려서 공격할 수 있다."),
    LEAFGUARD("Leaf Guard", "리프가드", "날씨가 맑을 때는 상태 이상이 되지 않는다."),
    KLUTZ("Klutz", "서투름", "지니고 있는 도구를 쓸 수 없다."),
    MOLDBREAKER("Mold Breaker", "틀깨기", "상대 특성에 방해받지 않고 상대에게 기술을 쓸 수 있다."),
    SUPERLUCK("Super Luck", "대운", "대운을 가지고 있어 상대의 급소에 공격이 맞기 쉽다."),
    AFTERMATH("Aftermath", "유폭", "기절했을 때 접촉한 상대에게 데미지를 준다."),
    ANTICIPATION("Anticipation", "위험예지", "상대가 지닌 위험한 기술을 감지할 수 있다."),
    FOREWARN("Forewarn", "예지몽", "등장했을 때 상대가 지닌 기술을 하나만 꿰뚫어본다."),
    UNAWARE("Unaware", "천진", "상대의 능력 변화를 무시하고 공격할 수 있다."),
    TINTEDLENS("Tinted Lens", "색안경", "효과가 별로인 기술을 통상의 위력으로 쓸 수 있다."),
    FILTER("Filter", "필터", "효과가 굉장한 공격의 위력을 약하게 만든다."),
    SLOWSTART("Slow Start", "슬로스타트", "5턴 동안 공격과 스피드가 절반이 된다."),
    SCRAPPY("Scrappy", "배짱", "고스트타입 포켓몬에게 노말타입과 격투타입의 기술을 맞게 한다."),
    STORMDRAIN("Storm Drain", "마중물", "물타입의 기술을 자신에게 끌어모아 데미지는 받지 않고 특수공격이 올라간다."),
    ICEBODY("Ice Body", "아이스바디", "날씨가 눈일 때 HP를 조금씩 회복한다."),
    SOLIDROCK("Solid Rock", "하드록", "효과가 굉장한 공격의 위력을 약하게 만든다."),
    SNOWWARNING("Snow Warning", "눈퍼뜨리기", "등장했을 때 날씨를 눈으로 만든다."),
    HONEYGATHER("Honey Gather", "꿀모으기", "배틀이 끝났을 때 달콤한꿀을 주울 때가 있다. 배틀 후에 꿀을 팔아 돈을 받을 수 있다."),
    FRISK("Frisk", "통찰", "등장했을 때 상대의 특성을 통찰할 수 있다."),
    RECKLESS("Reckless", "이판사판", "반동 데미지를 받는 기술의 위력이 올라간다."),
    MULTITYPE("Multitype", "멀티타입", "지니고 있는 플레이트나 Z크리스탈 타입에 따라 자신의 타입이 바뀐다."),
    FLOWERGIFT("Flower Gift", "플라워기프트", "날씨가 맑을 때 자신과 같은 편의 공격과 특수방어의 능력이 올라간다."),
    BADDREAMS("Bad Dreams", "나이트메어", "잠듦 상태의 상대에게 데미지를 준다."),
    PICKPOCKET("Pickpocket", "나쁜손버릇", "접촉한 상대의 도구를 훔친다."),
    SHEERFORCE("Sheer Force", "우격다짐", "기술의 추가 효과가 없어지지만 그만큼 높은 위력으로 기술을 사용할 수 있다."),
    CONTRARY("Contrary", "심술꾸러기", "능력의 변화가 역전해서 올라갈 때 떨어지고 떨어질 때 올라간다."),
    UNNERVE("Unnerve", "긴장감", "상대를 긴장시켜 나무열매를 먹지 못하게 한다."),
    DEFIANT("Defiant", "오기", "능력이 떨어지면 공격이 크게 올라간다."),
    DEFEATIST("Defeatist", "무기력", "HP가 절반이 되면 무기력해져서 공격과 특수공격이 반감된다."),
    CURSEDBODY("Cursed Body", "저주받은바디", "공격을 받으면 상대의 기술을 사슬묶기 상태로 만들 때가 있다."),
    HEALER("Healer", "치유의마음", "같은 편의 상태 이상을 가끔 회복시킨다."),
    FRIENDGUARD("Friend Guard", "프렌드가드", "같은 편의 데미지를 줄일 수 있다."),
    WEAKARMOR("Weak Armor", "깨어진갑옷", "물리 기술로 데미지를 받으면 방어가 떨어지고 스피드가 크게 올라간다."),
    HEAVYMETAL("Heavy Metal", "헤비메탈", "자신의 무게가 2배가 된다."),
    LIGHTMETAL("Light Metal", "라이트메탈", "자신의 무게가 절반이 된다."),
    MULTISCALE("Multiscale", "멀티스케일", "HP가 꽉 찼을 때 받는 데미지가 줄어든다."),
    TOXICBOOST("Toxic Boost", "독폭주", "독 상태가 되었을 때 물리 기술의 위력이 올라간다."),
    FLAREBOOST("Flare Boost", "열폭주", "화상 상태가 되었을 때 특수 기술의 위력이 올라간다."),
    HARVEST("Harvest", "수확", "사용한 나무열매를 몇 번이고 만들어 낸다."),
    TELEPATHY("Telepathy", "텔레파시", "같은 편의 공격의 낌새를 읽고 기술을 회피한다."),
    MOODY("Moody", "변덕쟁이", "매 턴 능력 중 하나가 크게 오르고 하나가 떨어진다."),
    OVERCOAT("Overcoat", "방진", "모래바람이나 싸라기눈 등의 데미지를 입지 않는다. 가루의 기술을 받지 않는다."),
    POISONTOUCH("Poison Touch", "독수", "접촉하기만 해도 상대를 독 상태로 만들 때가 있다."),
    REGENERATOR("Regenerator", "재생력", "지닌 포켓몬으로 돌아오면 HP를 조금 회복한다."),
    BIGPECKS("Big Pecks", "부풀린가슴", "방어를 떨어뜨리는 효과를 받지 않는다."),
    SANDRUSH("Sand Rush", "모래헤치기", "날씨가 모래바람일 때 스피드가 올라간다."),
    WONDERSKIN("Wonder Skin", "미라클스킨", "변화 기술을 받기 어려운 몸으로 되어 있다."),
    ANALYTIC("Analytic", "애널라이즈", "제일 마지막에 기술을 쓰면 기술의 위력이 올라간다."),
    ILLUSION("Illusion", "일루전", "지닌 포켓몬 중 제일 뒤에 있는 포켓몬으로 둔갑하여 나와서 상대를 속인다."),
    IMPOSTER("Imposter", "괴짜", "눈앞의 포켓몬으로 변신해버린다."),
    INFILTRATOR("Infiltrator", "틈새포착", "상대의 벽이나 대타출동을 뚫고 공격할 수 있다."),
    MUMMY("Mummy", "미라", "상대가 접촉하면 상대를 미라로 만들어버린다."),
    MOXIE("Moxie", "자기과신", "상대를 쓰러뜨리면 자신감이 붙어서 공격이 올라간다."),
    JUSTIFIED("Justified", "정의의마음", "악타입 공격을 받으면 정의감으로 공격이 올라간다."),
    RATTLED("Rattled", "주눅", "위협이나 악타입과 고스트타입과 벌레타입의 기술에 주눅이 들어 스피드가 올라간다."),
    MAGICBOUNCE("Magic Bounce", "매직미러", "상대가 쓴 변화 기술을 받지 않고 그대로 되받아칠 수 있다."),
    SAPSIPPER("Sap Sipper", "초식", "풀타입 기술을 받으면 데미지를 입지 않고 공격이 올라간다."),
    PRANKSTER("Prankster", "짓궂은마음", "변화 기술을 먼저 쓸 수 있다."),
    SANDFORCE("Sand Force", "모래의힘", "날씨가 모래바람일 때 바위타입과 땅타입과 강철타입의 위력이 올라간다."),
    IRONBARBS("Iron Barbs", "철가시", "자신과 접촉한 상대에게 철가시로 데미지를 준다."),
    ZENMODE("Zen Mode", "달마모드", "HP가 절반 이하가 되면 모습이 변화한다."),
    VICTORYSTAR("Victory Star", "승리의별", "자신과 같은 편의 명중률이 올라간다."),
    TURBOBLAZE("Turboblaze", "터보블레이즈", "상대 특성에 방해받지 않고 상대에게 기술을 쓸 수 있다."),
    TERAVOLT("Teravolt", "테라볼티지", "상대 특성에 방해받지 않고 상대에게 기술을 쓸 수 있다."),
    AROMAVEIL("Aroma Veil", "아로마베일", "자신과 같은 편으로 향하는 멘탈 공격을 막을 수 있다."),
    FLOWERVEIL("Flower Veil", "플라워베일", "같은 편의 풀타입 포켓몬은 능력이 떨어지지 않고 상태 이상도 되지 않는다."),
    CHEEKPOUCH("Cheek Pouch", "볼주머니", "어떤 나무열매라도 먹으면 HP도 회복한다."),
    PROTEAN("Protean", "변환자재", "자신이 사용한 기술과 같은 타입으로 변화한다."),
    FURCOAT("Fur Coat", "퍼코트", "상대로부터 받는 물리 기술의 데미지가 절반이 된다."),
    MAGICIAN("Magician", "매지션", "기술을 맞은 상대의 도구를 빼앗아 버린다."),
    BULLETPROOF("Bulletproof", "방탄", "상대 구슬이나 폭탄 등 기술을 막을 수 있다."),
    COMPETITIVE("Competitive", "승기", "능력이 떨어지면 특수공격이 크게 올라간다."),
    STRONGJAW("Strong Jaw", "옹골찬턱", "턱이 튼튼하여 무는 기술의 위력이 올라간다."),
    REFRIGERATE("Refrigerate", "프리즈스킨", "노말타입의 기술이 얼음타입이 된다. 위력이 조금 올라간다."),
    SWEETVEIL("Sweet Veil", "스위트베일", "같은 편의 포켓몬이 잠들지 않게 된다."),
    STANCECHANGE("Stance Change", "배틀스위치", "공격 기술을 쓰면 블레이드폼으로 기술 킹실드를 쓰면 실드폼으로 변한다."),
    GALEWINGS("Gale Wings", "질풍날개", "HP가 꽉 찼을 때 비행타입의 기술을 먼저 쓸 수 있다."),
    MEGALAUNCHER("Mega Launcher", "메가런처", "파동 기술의 위력이 올라간다."),
    GRASSPELT("Grass Pelt", "풀모피", "그래스필드일 때 방어가 올라간다."),
    SYMBIOSIS("Symbiosis", "공생", "같은 편이 도구를 쓰면 자신이 지니고 있는 도구를 같은 편에게 건넨다."),
    TOUGHCLAWS("Tough Claws", "단단한발톱", "상대에게 접촉하는 기술의 위력이 올라간다."),
    PIXILATE("Pixilate", "페어리스킨", "노말타입의 기술이 페어리타입이 된다. 위력이 조금 올라간다."),
    GOOEY("Gooey", "미끈미끈", "공격으로 자신과 접촉한 상대의 스피드를 떨어뜨린다."),
    AERILATE("Aerilate", "스카이스킨", "노말타입의 기술이 비행타입이 된다. 위력이 조금 올라간다."),
    PARENTALBOND("Parental Bond", "부자유친", "부모와 자식 2마리로 2번 공격할 수 있다."),
    DARKAURA("Dark Aura", "다크오라", "전원의 악타입 기술이 강해진다."),
    FAIRYAURA("Fairy Aura", "페어리오라", "전원의 페어리타입 기술이 강해진다."),
    AURABREAK("Aura Break", "오라브레이크", "오라의 효과를 역전시켜 위력을 떨어뜨린다."),
    PRIMORDIALSEA("Primordial Sea", "시작의바다", "불꽃타입의 공격을 받지 않는 날씨로 만든다."),
    DESOLATELAND("Desolate Land", "끝의대지", "물타입의 공격을 받지 않는 날씨로 만든다."),
    DELTASTREAM("Delta Stream", "델타스트림", "비행타입의 약점이 없어지는 날씨로 만든다."),
    STAMINA("Stamina", "지구력", "공격을 받으면 방어가 올라간다."),
    WIMPOUT("Wimp Out", "도망태세", "HP가 절반이 되면 황급히 도망쳐서 지닌 포켓몬으로 돌아간다."),
    EMERGENCYEXIT("Emergency Exit", "위기회피", "HP가 절반이 되면 위험을 회피하기 위해 지닌 포켓몬으로 돌아간다."),
    WATERCOMPACTION("Water Compaction", "꾸덕꾸덕굳기", "물타입의 기술을 받으면 방어가 크게 올라간다."),
    MERCILESS("Merciless", "무도한행동", "독 상태의 상대를 공격하면 반드시 급소에 맞는다."),
    SHIELDSDOWN("Shields Down", "리밋실드", "HP가 절반이 되면 껍질이 깨져 공격적으로 된다."),
    STAKEOUT("Stakeout", "잠복", "교체로 나온 상대에게 2배 데미지로 공격할 수 있다."),
    WATERBUBBLE("Water Bubble", "수포", "자신을 향하는 불꽃타입 기술의 위력을 떨어뜨린다. 화상을 입지 않는다."),
    STEELWORKER("Steelworker", "강철술사", "강철타입 기술의 위력이 올라간다."),
    BERSERK("Berserk", "발끈", "상대의 공격으로 HP가 절반이 되면 특수공격이 올라간다."),
    SLUSHRUSH("Slush Rush", "눈치우기", "날씨가 눈일 때 스피드가 올라간다."),
    LONGREACH("Long Reach", "원격", "모든 기술을 상대에게 접촉하지 않고 사용할 수 있다."),
    LIQUIDVOICE("Liquid Voice", "촉촉보이스", "모든 소리 기술이 물타입이 된다."),
    TRIAGE("Triage", "힐링시프트", "회복 기술을 먼저 사용할 수 있다."),
    GALVANIZE("Galvanize", "일렉트릭스킨", "노말타입 기술이 전기타입이 된다. 위력이 조금 올라간다."),
    SURGESURFER("Surge Surfer", "서핑테일", "일렉트릭필드일 때 스피드가 2배가 된다."),
    SCHOOLING("Schooling", "어군", "HP가 많을 때 무리지어 강해진다. HP가 얼마 남지 않으면 무리는 뿔뿔이 흩어진다."),
    DISGUISE("Disguise", "탈", "몸을 덮는 탈로 1번 공격을 막을 수 있다."),
    BATTLEBOND("Battle Bond", "유대변화", "상대를 쓰러뜨리면 트레이너와의 유대감이 깊어져서 지우개굴닌자로 변한다. 물수리검이 강해진다."),
    POWERCONSTRUCT("Power Construct", "스웜체인지", "HP가 절반이 되면 셀들이 응원하러 달려와 퍼펙트폼으로 모습이 변한다."),
    CORROSION("Corrosion", "부식", "강철타입이나 독타입도 독 상태로 만들 수 있다."),
    COMATOSE("Comatose", "절대안깸", "항상 비몽사몽 상태로 절대 깨지 않는다. 잠든 상태로 공격할 수 있다."),
    QUEENLYMAJESTY("Queenly Majesty", "여왕의위엄", "상대에게 위압감을 줘서 이쪽을 향한 선제 기술을 사용할 수 없게 한다."),
    INNARDSOUT("Innards Out", "내용물분출", "상대가 쓰러뜨렸을 때 HP의 남은 양만큼 상대에게 데미지를 준다."),
    DANCER("Dancer", "무희", "누군가 춤 기술을 쓰면 자신도 이어서 춤 기술을 쓸 수 있다."),
    BATTERY("Battery", "배터리", "같은 편 특수 기술의 위력을 올린다."),
    FLUFFY("Fluffy", "복슬복슬", "상대로부터 받은 접촉하는 기술의 데미지를 반감시키지만 불꽃타입 기술의 데미지는 2배가 된다."),
    DAZZLING("Dazzling", "비비드바디", "상대를 놀라게 해서 이쪽을 향한 선제 기술을 사용할 수 없게 한다."),
    SOULHEART("Soul-Heart", "소울하트", "포켓몬이 기절할 때마다 특수공격이 올라간다."),
    TANGLINGHAIR("Tangling Hair", "컬리헤어", "공격으로 자신에게 접촉한 상대의 스피드를 떨어뜨린다."),
    RECEIVER("Receiver", "리시버", "쓰러진 같은 편의 특성을 이어받아 같은 특성으로 바뀐다."),
    POWEROFALCHEMY("Power of Alchemy", "화학의힘", "쓰러진 같은 편의 특성을 이어받아 같은 특성으로 바뀐다."),
    BEASTBOOST("Beast Boost", "비스트부스트", "상대를 기절시켰을 때 자신의 가장 높은 능력이 올라간다."),
    RKSSYSTEM("RKS System", "AR시스템", "지니고 있는 메모리로 자신의 타입이 변한다."),
    ELECTRICSURGE("Electric Surge", "일렉트릭메이커", "등장했을 때 일렉트릭필드를 사용한다."),
    PSYCHICSURGE("Psychic Surge", "사이코메이커", "등장했을 때 사이코필드를 사용한다."),
    MISTYSURGE("Misty Surge", "미스트메이커", "등장했을 때 미스트필드를 사용한다."),
    GRASSYSURGE("Grassy Surge", "그래스메이커", "등장했을 때 그래스필드를 사용한다."),
    FULLMETALBODY("Full Metal Body", "메탈프로텍트", "상대 기술이나 특성으로 능력이 떨어지지 않는다."),
    SHADOWSHIELD("Shadow Shield", "스펙터가드", "HP가 꽉 찼을 때 받는 데미지가 줄어든다."),
    PRISMARMOR("Prism Armor", "프리즘아머", "효과가 굉장한 공격의 위력을 약하게 만든다."),
    NEUROFORCE("Neuroforce", "브레인포스", "효과가 굉장한 공격의 위력이 더욱 올라간다."),
    INTREPIDSWORD("Intrepid Sword", "불요의검", "등장했을 때 공격이 올라간다."),
    DAUNTLESSSHIELD("Dauntless Shield", "불굴의방패", "등장했을 때 방어가 올라간다."),
    LIBERO("Libero", "리베로", "자신이 사용한 기술과 같은 타입으로 변화한다."),
    BALLFETCH("Ball Fetch", "볼줍기", "첫 번째로 실패한 몬스터볼을 주워온다."),
    COTTONDOWN("Cotton Down", "솜털", "공격을 받으면 솜털을 흩뿌려서 자신을 제외한 모든 포켓몬의 스피드를 떨어뜨린다."),
    PROPELLERTAIL("Propeller Tail", "스크루지느러미", "상대의 기술을 끌어모으는 특성이나 기술의 영향을 받지 않는다."),
    MIRRORARMOR("Mirror Armor", "미러아머", "자신이 받는 능력 다운 효과에 한해 되받아친다."),
    GULPMISSILE("Gulp Missile", "그대로꿀꺽미사일", "파도타기나 다이빙을 쓰면 먹이를 물어온다. 데미지를 받으면 먹이를 토해내서 공격한다."),
    STALWART("Stalwart", "굳건한신념", "상대의 기술을 끌어모으는 특성이나 기술의 영향을 받지 않는다."),
    STEAMENGINE("Steam Engine", "증기기관", "물타입이나 불꽃타입 기술을 받으면 스피드가 매우 크게 올라간다."),
    PUNKROCK("Punk Rock", "펑크록", "소리 기술의 위력이 올라간다. 상대로부터 받는 소리 기술의 데미지는 절반이 된다."),
    SANDSPIT("Sand Spit", "모래뿜기", "공격을 받으면 모래바람을 일으킨다."),
    ICESCALES("Ice Scales", "얼음인분", "얼음인분의 보호를 받아 특수공격으로 받는 데미지가 절반이 된다."),
    RIPEN("Ripen", "숙성", "나무열매를 숙성시켜서 효과가 2배가 된다."),
    ICEFACE("Ice Face", "아이스페이스", "물리공격을 머리의 얼음이 대신 맞아주지만 모습도 바뀐다. 얼음은 싸라기눈이 내리면 원래대로 돌아온다."),
    POWERSPOT("Power Spot", "파워스폿", "옆에 있기만 해도 기술의 위력이 올라간다."),
    MIMICRY("Mimicry", "의태", "필드의 상태에 따라 포켓몬의 타입이 바뀐다."),
    SCREENCLEANER("Screen Cleaner", "배리어프리", "등장했을 때 상대와 같은 편의 빛의장막, 리플렉터, 오로라베일의 효과가 사라진다."),
    STEELYSPIRIT("Steely Spirit", "강철정신", "같은 편의 강철타입 공격의 위력이 올라간다."),
    PERISHBODY("Perish Body", "멸망의바디", "접촉하는 기술을 받으면 3턴 후에 양쪽 모두 기절한다. 교체되면 효과가 없어진다."),
    WANDERINGSPIRIT("Wandering Spirit", "떠도는영혼", "접촉하는 기술로 공격해온 포켓몬과 특성을 바꾼다."),
    GORILLATACTICS("Gorilla Tactics", "무아지경", "공격이 올라가지만 처음에 선택한 기술 외에는 쓸 수 없게 된다."),
    NEUTRALIZINGGAS("Neutralizing Gas", "화학변화가스", "화학변화가스를 가진 포켓몬이 배틀에 나와 있으면 모든 포켓몬이 가진 특성의 효과가 사라지거나 발동하지 않게 된다."),
    PASTELVEIL("Pastel Veil", "파스텔베일", "자신과 같은 편이 독의 상태 이상 효과를 받지 않게 된다."),
    HUNGERSWITCH("Hunger Switch", "꼬르륵스위치", "턴이 끝날 때마다 배부른 모양, 배고픈 모양, 배부른 모양...으로 번갈아서 모습을 바꾼다."),
    QUICKDRAW("Quick Draw", "퀵드로", "상대보다 먼저 행동할 수도 있다."),
    UNSEENFIST("Unseen Fist", "보이지않는주먹", "상대에게 접촉하는 기술을 사용하면 방어의 효과를 무시하고 공격할 수 있다."),
    CURIOUSMEDICINE("Curious Medicine", "기묘한약", "등장했을 때 조개껍질에서 약을 흩뿌려서 능력 변화를 원래대로 되돌린다."),
    TRANSISTOR("Transistor", "트랜지스터", "전기타입 기술의 위력이 올라간다."),
    DRAGONSMAW("Dragon's Maw", "용의턱", "드래곤타입 기술의 위력이 올라간다."),
    CHILLINGNEIGH("Chilling Neigh", "백의울음", "상대를 쓰러뜨리면 차가운 울음소리를 내면서 공격이 올라간다."),
    GRIMNEIGH("Grim Neigh", "흑의울음", "상대를 쓰러뜨리면 무서운 울음소리를 내면서 특수공격이 올라간다."),
    ASONEGLASTRIER("As One", "혼연일체", "버드렉스의 긴장감과 블리자포스의 백의울음 두 가지 특성을 겸비한다."),
    ASONESPECTRIER("As One", "혼연일체", "버드렉스의 긴장감과 레이스포스의 흑의울음 두 가지 특성을 겸비한다."),
    LINGERINGAROMA("Lingering Aroma", "가시지않는향기", "상대가 접촉하면 가시지 않는 향기가 상대에게 배어 버린다."),
    SEEDSOWER("Seed Sower", "넘치는씨", "공격을 받으면 필드를 그래스필드로 만든다."),
    THERMALEXCHANGE("Thermal Exchange", "열교환", "불꽃타입 기술로 공격받으면 공격이 올라간다. 화상 상태가 되지 않는다."),
    ANGERSHELL("Anger Shell", "분노의껍질", "상대의 공격에 의해 HP가 절반이 되면 화가 나서 방어와 특수방어가 떨어지지만 공격, 특수공격, 스피드가 올라간다."),
    PURIFYINGSALT("Purifying Salt", "정화의소금", "깨끗한 소금에 의해 상태 이상이 되지 않는다. 고스트타입 기술의 데미지를 반감시킨다."),
    WELLBAKEDBODY("Well-Baked Body", "노릇노릇바디", "불꽃타입 기술로 공격받으면 데미지를 입지 않고 방어가 크게 올라간다."),
    WINDRIDER("Wind Rider", "바람타기", "순풍이 불거나 바람 기술로 공격받으면 데미지를 받지 않고 공격이 올라간다."),
    GUARDDOG("Guard Dog", "파수견", "위협을 받으면 공격이 올라간다. 포켓몬을 교체시키는 기술이나 도구의 효과를 받지 않는다."),
    ROCKYPAYLOAD("Rocky Payload", "바위나르기", "바위타입 기술의 위력이 올라간다."),
    WINDPOWER("Wind Power", "풍력발전", "바람 기술로 공격받으면 충전 상태가 된다."),
    ZEROTOHERO("Zero to Hero", "마이티체인지", "지닌 포켓몬으로 돌아오면 마이티폼으로 변한다."),
    COMMANDER("Commander", "사령탑", "등장했을 때 같은 편에 어써러셔가 있으면 입속에 들어가 안에서 지시를 내린다."),
    ELECTROMORPHOSIS("Electromorphosis", "전기로바꾸기", "데미지를 받으면 충전 상태가 된다."),
    PROTOSYNTHESIS("Protosynthesis", "고대활성", "부스트에너지를 지니고 있거나 날씨가 맑을 때 가장 높은 능력이 올라간다."),
    QUARKDRIVE("Quark Drive", "쿼크차지", "부스트에너지를 지니고 있거나 일렉트릭필드일 때 가장 높은 능력이 올라간다."),
    GOODASGOLD("Good as Gold", "황금몸", "산화하지 않는 튼튼한 황금몸 덕분에 상대의 변화 기술의 영향을 받지 않는다."),
    VESSELOFRUIN("Vessel of Ruin", "재앙의그릇", "재앙을 부르는 그릇의 힘으로 자신을 제외한 모든 포켓몬의 특수 공격을 약하게 만든다."),
    SWORDOFRUIN("Sword of Ruin", "재앙의검", "재앙을 부르는 검의 힘으로 자신을 제외한 모든 포켓몬의 방어를 약하게 만든다."),
    TABLETSOFRUIN("Tablets of Ruin", "재앙의목간", "재앙을 부르는 목간의 힘으로 자신을 제외한 모든 포켓몬의 공격을 약하게 만든다."),
    BEADSOFRUIN("Beads of Ruin", "재앙의구슬", "재앙을 부르는 곡옥의 힘으로 자신을 제외한 모든 포켓몬의 특수방어를 약하게 만든다."),
    ORICHALCUMPULSE("Orichalcum Pulse", "진홍빛고동", "등장했을 때 날씨를 맑음으로 만든다. 햇살이 강하면 고대의 고동에 의해 공격이 강화된다."),
    HADRONENGINE("Hadron Engine", "하드론엔진", "등장했을 때 일렉트릭필드를 전개한다. 일렉트릭필드일 때 미래 기관에 의해 특수공격이 강화된다."),
    OPPORTUNIST("Opportunist", "편승", "상대의 능력이 올라가면 자신도 편승해서 똑같이 자신도 올린다."),
    CUDCHEW("Cud Chew", "되새김질", "한 번에 한하여 나무열매를 먹으면 다음 턴이 끝날 때 위에서 꺼내서 또 먹는다."),
    SHARPNESS("Sharpness", "예리함", "상대를 베는 기술의 위력이 올라간다."),
    SUPREMEOVERLORD("Supreme Overlord", "총대장", "등장했을 때 지금까지 쓰러진 같은 편의 수가 많을수록 조금씩 공격과 특수공격이 올라간다."),
    COSTAR("Costar", "협연", "등장했을 때 같은 편의 능력 변화를 복사한다."),
    TOXICDEBRIS("Toxic Debris", "독치장", "물리 기술로 데미지를 받으면 상대의 발밑에 독압정을 뿌린다."),
    ARMORTAIL("Armor Tail", "테일아머", "머리를 감싸고 있는 수수께끼의 꼬리가 이쪽을 향한 선제 기술을 사용할 수 없게 한다."),
    EARTHEATER("Earth Eater", "흙먹기", "땅타입의 기술로 공격받으면 데미지를 받지 않고 회복한다."),
    MYCELIUMMIGHT("Mycelium Might", "균사의힘", "변화 기술을 사용할 때 반드시 행동이 느려지지만 상대 특성에 방해받지 않는다."),
    MINDSEYE("Mind's Eye", "심안", "노말타입과 격투타입 기술을 고스트타입에게 맞힐 수 있다. 상대의 회피율 변화를 무시하고 명중률도 떨어지지 않는다."),
    SUPERSWEETSYRUP("Supersweet Syrup", "감미로운꿀", "처음 등장했을 때 감미로운 꿀의 향기를 흩뿌려서 상대의 회피율을 떨어뜨린다."),
    HOSPITALITY("Hospitality", "대접", "등장했을 때 같은 편을 대접해서 HP를 조금 회복시킨다."),
    TOXICCHAIN("Toxic Chain", "독사슬", "독소를 머금은 사슬의 힘으로 기술에 맞은 상대를 맹독 상태로 만들 때가 있다."),
    EMBODYASPECTTEAL("Embody Aspect", "초상투영", "마음속에 깃든 추억의 힘으로 벽록의가면을 빛나게 하여 자신의 스피드를 올린다."),
    EMBODYASPECTWELLSPRING("Embody Aspect", "초상투영", "마음속에 깃든 추억의 힘으로 우물의가면을 빛나게 하여 자신의 특수방어를 올린다."),
    EMBODYASPECTHEARTHFLAME("Embody Aspect", "초상투영", "마음속에 깃든 추억의 힘으로 화덕의가면을 빛나게 하여 자신의 공격력을 올린다."),
    EMBODYASPECTCORNERSTONE("Embody Aspect", "초상투영", "마음속에 깃든 추억의 힘으로 주춧돌의가면을 빛나게 하여 자신의 방어력을 올린다."),
    TERASHIFT("Tera Shift", "테라체인지", "등장했을 때 주위의 에너지를 흡수하여 테라스탈폼으로 변한다."),
    TERASHELL("Tera Shell", "테라셸", "모든 타입의 힘이 담긴 등껍질이 HP가 꽉 찼을 때 받는 데미지를 모두 효과가 별로이게 만든다."),
    TERAFORMZERO("Teraform Zero", "제로포밍", "테라파고스가 스텔라폼이 되었을 때 숨겨진 힘에 의해 날씨와 필드의 영향을 모두 무효로 만든다."),
    POISONPUPPETEER("Poison Puppeteer", "독조종", "복숭악동의 기술에 의해 독 상태가 된 상대는 혼란 상태도 되어 버린다."),
    EMPTY("empty", "empty", "empty"),
    ;

    private final String id;
    private final String name;
    private final String description;

    Ability(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public static Ability findById(String id) {
        return Arrays.stream(values())
                .filter(value -> value.getId()
                        .replace("-", "_")
                        .replace(" ", "_")
                        .toLowerCase()
                        .equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 특성 아이디입니다."));
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
