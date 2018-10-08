package yun.zudic;

public class ZhuyinConverter {
    public String Converter(String s){
        String converted = s.replace("0", "·").replace("1", " ").replace("2", "ˊ").replace("3", "ˇ").replace("4", "ˋ");
        converted = converted.replace("iang", "ㄧㄤ").replace("uang", "ㄨㄤ").replace("iong", "ㄩㄥ");
        converted = converted.replace("ang", "ㄤ").replace("eng", "ㄥ").replace("iao", "ㄧㄠ").replace("ian", "ㄧㄢ").replace("ing", "ㄧㄥ").replace("uai", "ㄨㄞ").replace("uan", "ㄨㄢ").replace("ong", "ㄨㄥ").replace("üan", "ㄩㄢ").replace("yue", "ㄩㄝ");
        converted = converted.replace("ai", "ㄞ").replace("ei","ㄟ").replace("ao", "ㄠ").replace("ou", "ㄡ").replace("an", "ㄢ").replace("en", "ㄣ").replace("er", "ㄦ").replace("in", "ㄧㄣ").replace("un", "ㄨㄣ").replace("ün", "ㄩㄣ");
        converted = converted.replace("yi", "ㄧ").replace("ye", "ㄝ").replace("yu","ㄩ").replace("wu", "ㄨ");
        converted = converted.replace("a", "ㄚ").replace("i", "ㄧ").replace("u", "ㄨ").replace("ü", "ㄩ").replace("o", "ㄛ").replace("e", "ㄜ").replace("w", "ㄨ");
        converted = converted.replace("b", "ㄅ").replace("p", "ㄆ").replace("m", "ㄇ").replace("f", "ㄈ").replace("d", "ㄉ").replace("t", "ㄊ").replace("n", "ㄋ").replace("l", "ㄌ").replace("g", "ㄍ").replace("k", "ㄎ").replace("j", "ㄐ").replace("q", "ㄑ").replace("x", "ㄒ");
        converted = converted.replace("zh", "ㄓ").replace("ch", "ㄔ").replace("sh", "ㄕ").replace("r", "ㄖ");
        converted = converted.replace("z", "ㄗ").replace("c", "ㄘ").replace("s", "ㄙ").replace("h", "ㄏ");
        return converted;
    }
}
