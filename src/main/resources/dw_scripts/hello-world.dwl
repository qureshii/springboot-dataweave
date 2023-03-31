%dw 2.0
output application/json
var foo = "Bar"
---
{
    "message": payload,
    "foo": foo

}