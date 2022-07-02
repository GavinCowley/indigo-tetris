package in.gav.tetris.assets

import indigo.{AssetName, AssetPath, AssetType, FontKey}

object Assets:

  object Block:
    val Unit: AssetName = AssetName("Unit")

    def assets(baseUrl: String): Set[AssetType] = Set(
      AssetType.Image(Unit, AssetPath(baseUrl + "assets/" + Unit + ".png")),
    )

  object Text:
    val Font: AssetName = AssetName("UnscreenMK")
    val Key: FontKey = FontKey(Font.toString)

    def assets(baseUrl: String): Set[AssetType] = Set(
      AssetType.Image(Font, AssetPath(baseUrl + "assets/" + Font + ".png")),
      AssetType.Text(Font, AssetPath(baseUrl + "assets/" + Font + ".json"))
    )

  object Button:
    val Graphic: AssetName = AssetName("Button")

    def assets(baseUrl: String): Set[AssetType] = Set(
      AssetType.Image(Graphic, AssetPath(baseUrl + "assets/" + Graphic + ".png"))
    )

  def bootAssets(baseUrl: String): Set[AssetType] = Block.assets(baseUrl) ++ Text.assets(baseUrl) ++ Button.assets(baseUrl)

  def remainingAssets(baseUrl: String): Set[AssetType] = Set.empty
