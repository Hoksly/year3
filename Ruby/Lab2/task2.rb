
$P = 5
$t = 16
$r = 8

$result = $P**$r*(1 - $P**(-1*$t))

print "result = " + $result.to_f.to_s, "\n"