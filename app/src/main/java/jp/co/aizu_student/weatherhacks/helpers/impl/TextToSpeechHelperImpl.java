package jp.co.aizu_student.weatherhacks.helpers.impl;

import android.content.Context;
import android.os.Build;
import android.speech.tts.TextToSpeech;

import java.util.HashMap;
import java.util.Locale;

import jp.co.aizu_student.weatherhacks.helpers.TextToSpeechHelper;
import jp.co.aizu_student.weatherhacks.models.Forecast;
import jp.co.aizu_student.weatherhacks.models.Location;
import jp.co.aizu_student.weatherhacks.models.Temperature;

public class TextToSpeechHelperImpl implements TextToSpeechHelper {
    private TextToSpeech textToSpeech;
    private Context context;

    private TextToSpeech.OnInitListener onInitListener = status -> {
        if (status == TextToSpeech.SUCCESS && textToSpeech != null) {
            textToSpeech.setPitch(1.0f);
            textToSpeech.setSpeechRate(1.0f);
            textToSpeech.setLanguage(Locale.JAPAN);
        }
    };

    @Override
    public void init(Context context) {
        this.context = context;
        textToSpeech = new TextToSpeech(context, onInitListener);
    }

    @Override
    public void onResume() {
        if (textToSpeech == null) {
            init(context);
        }
    }

    @Override
    public void onPause() {
        if (textToSpeech != null) {
            textToSpeech.stop();
        }
    }

    @Override
    public void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.shutdown();
            textToSpeech = null;
        }
    }

    @Override
    public void talk(String sentence) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textToSpeech.speak(sentence, TextToSpeech.QUEUE_FLUSH, null, null);
        } else {
            String UTTERANCE_ID = "SPEECH";
            HashMap<String, String> ttsParam = new HashMap<>();
            ttsParam.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, UTTERANCE_ID);

            sentence = sentence.replace("曇のち雨", "曇のちあめ");

            textToSpeech.speak(sentence, TextToSpeech.QUEUE_FLUSH, ttsParam);
        }
    }

    @Override
    public void talkWeather(Forecast forecast) {
        talk(forecast.getTelop());
    }

    @Override
    public void talkTemperature(Temperature temperature) {
        talk("最高気温は、"
                + temperature.getMax().get(Temperature.CELSIUS).replaceAll("-", "マイナス")
                + "度、最低気温は、"
                + temperature.getMin().get(Temperature.CELSIUS).replaceAll("-", "マイナス")
                + "度です。"
        );
    }

    @Override
    public void talkWeatherWithTemperature(
            String whatDay, Location location, Forecast forecast, Temperature temperature) {

        boolean hot = false;
        boolean cold = false;

        String maxTemp = "";
        if (temperature.getMax() != null) {
            maxTemp = "最高気温は、" + temperature.getMax().get(Temperature.CELSIUS).replaceAll("-", "マイナス") + "度";
            hot = Integer.valueOf(temperature.getMax().get(Temperature.CELSIUS)) > 20;
        }

        String minTemp = "";
        if (temperature.getMin() != null) {
            minTemp = "、最低気温は、" + temperature.getMin().get(Temperature.CELSIUS).replaceAll("-", "マイナス") + "度";
            cold = Integer.valueOf(temperature.getMin().get(Temperature.CELSIUS)) < 9;
        }

        String suffix = "";

        if (temperature.getMin() != null || temperature.getMin() != null) {
            suffix = "です。";
        }

        if (hot) {
            suffix += "熱いですね！！こまめな水分補給を忘れずに！";
        } else if (cold) {
            suffix += "寒いですね。あ、あの。もう少し、近くに寄っても、いいですか？";
        } else if (forecast.getTelop().contains("雨") || forecast.getTelop().contains("雪")) {
            suffix += "傘を持って行ったほうが良さそうですね！";
        } else if (temperature.getMax() != null
                && temperature.getMin() != null
                && forecast.getTelop().contains("晴")) {
            suffix += "過ごしやすい日ですね！折角ですし、どこか出かけに行きませんか？";
        }

        talk(location.getPrefecture() + location.getCity()
                + "の、" + whatDay + "の天気は、" + forecast.getTelop() + "です。"
                + maxTemp + minTemp + suffix
        );
    }
}
