use DBI;

my $dbh = DBI->connect("DBI:mysql:database=test;host=192.168.1.200","root","root", {'RaiseError' => 1});
my $rows = $dbh->do("INSERT INTO t_name(name, age) VALUES ('test','28')");
my $sqr = $dbh->prepare("SELECT name FROM t_name");
$sqr->execute();
while(my $ref = $sqr->fetchrow_hashref()) {
    print "$ref->{'name'}\n";
}
$dbh->disconnect();