package poke.rogue.helper.local.entity


fun pokemonEntity(
    id: String = "1",
    dexNumber: Long = 1,
    name: String = "이상해씨",
    imageUrl: String = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png",
    types: Set<String> = setOf("풀", "독"),
    generation: Int = 1,
    baseStat: Int = 318,
    hp: Int = 45,
    attack: Int = 49,
    defense: Int = 49,
    specialAttack: Int = 65,
    specialDefense: Int = 65,
    speed: Int = 45,
): PokemonEntity = PokemonEntity(
    id = id,
    dexNumber = dexNumber,
    name = name,
    imageUrl = imageUrl,
    types = types,
    generation = generation,
    baseStat = baseStat,
    hp = hp,
    attack = attack,
    defense = defense,
    specialAttack = specialAttack,
    specialDefense = specialDefense,
    speed = speed,
)