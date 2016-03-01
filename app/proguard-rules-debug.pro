# リリースビルド用のProgurdルールをインポートする
-include proguard-rules.pro

# テストとかのため、自信で記述したアプリケーションのクラスは保持
-keep class your.application.packagename.** { *; }

# シュリンクはする
#-dontshrink
# 難読化しない
-dontobfuscate
# 最適化しない
-dontoptimize
