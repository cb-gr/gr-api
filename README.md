## Example Usage

```
lein uberjar

java -jar ./target/gr-parser-0.1.0-SNAPSHOT-standalone.jar pipe_delimitted pipe :date-of-birth

java -jar ./target/gr-parser-0.1.0-SNAPSHOT-standalone.jar pipe_delimitted pipe :last-name

java -jar ./target/gr-parser-0.1.0-SNAPSHOT-standalone.jar "pipe_delimitted" pipe "[:gender :last-name]"
```