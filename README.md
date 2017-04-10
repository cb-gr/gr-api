## Example Usage of CLI

```
lein uberjar

java -jar ./target/gr-parser-0.1.0-SNAPSHOT-standalone.jar pipe_delimitted pipe :date-of-birth

java -jar ./target/gr-parser-0.1.0-SNAPSHOT-standalone.jar pipe_delimitted pipe :last-name

java -jar ./target/gr-parser-0.1.0-SNAPSHOT-standalone.jar "pipe_delimitted" pipe "[:gender :last-name]"
```

## Running the API:

Starting the API server on http://localhost:8080

```
lein run -m gr-parser.web/-main
```

## Example Requests

### Adding Records
```
curl -i -H 'Content-Type: application/json' -XPOST 'http://localhost:8080/records' -d '{
  "delimitter": "pipe",
  "data": "LAST | FIRST | Female | BLUE | 1/1/1111"
}
'

curl -i -H 'Content-Type: application/json' -XPOST 'http://localhost:8080/records' -d '{
  "delimitter": "pipe",
  "data": "Bui | Chris | Male | BLUE | 5/18/1111"
}
'
``

### Sorted Records
```
curl -i  -XGET 'http://localhost:8080/records/gender'
curl -i  -XGET 'http://localhost:8080/records/birthdate'
curl -i  -XGET 'http://localhost:8080/records/name'
```
