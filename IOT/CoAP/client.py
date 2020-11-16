from coapthon.client.helperclient import HelperClient

host = "172.19.85.129"
port = 5683
path ="basic"

client = HelperClient(server=(host, port))
response = client.get(path)
print response.pretty_print()
client.stop()
