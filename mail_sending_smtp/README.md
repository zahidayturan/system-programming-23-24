## Ödev Metni

26.10.2023 tarihinde işlenen Sistem Programlama uygulama dersinde, TELNET uygulaması kullanarak SMTP protokolüne ait EHLO, AUTH, MAIL FROM vb. metotlar ile basit bir mail gönderim uygulaması gerçeklenmişti.

Uygulama dersinde de görüldüğü gibi soket üzerinde çalışan çoğu protokolde (SMTP, HTTP, FTP vb.) sunucuda çalışması istenen metot ismi ve peşinden metodun uygulanacağı data (veri) gönderilmektedir. (Bu tarz protokoller, serileştirilmiş nesnelerle değil; karakter dizileri ile haberleşme yapmaktadır.)
Örn: “AUTH LOGIN encoded_mail_address” (flush)
https://github.com/ismailhakkituran/sending-mail-over-sockets-using-smtp/ adresinde anlatıldığı gibi sırasıyla:
1.	“README.md” dosyasındaki gmail uygulama giriş şifresi alınmalıdır.
2.	“sending mail over SSL Telnet” dosyasında verilen IP adresi ve soket numarasına soket bağlantısı açıp, sıra ile dosya içerisindeki metotlar gönderilmelidir.

Tercih ettiğiniz bir programlama dili ile soket bağlantısı açıp, sunucuya karakter dizileri (string) şeklinde gönderim ve alım fonksiyonlarını gerçekleyiniz.


İpuçları
•	“sending mail over SSL Telnet” dosyasında 6. satırdan itibaren tüm satırların sonunda telnet uygulamasında ENTER ile flush yapılmıştır. Sizler dosyadaki her satır için bir gönderim yapmak ve peşinden sunucudan kaç adet mesaj geliyorsa da o kadar dinleme yapmak zorundasınız.
•	“sending mail over SSL Telnet” dosyasında 6.satırdaki ilk EHLO komutundan 16.satırdaki QUIT metodu gönderimine kadar toplam 11 adet socket gönderimi (send) ve gönderilen her mesaj için de gerektiği kadar socket mesaj alımı (receive) yapmanız gerekmektedir.
•	Gönderilen her mesaj için kaç adet yanıt geldiğini tespit etmek de sizin görevinizdir.
•	Çalışan kod, tek sayfadan oluşan peşpeşe send(), receive() fonksiyonlarından ibarettir. 

## TELNET
# Secure telnet başlatmak için Linux/Unix Komut Satirina Giriniz
openssl s_client -connect smtp.gmail.com:465 -crlf -ign_eof

# Google sunucularina baglandiktan sonra sırasıyla aşağıdaki etiketli mesajları girip ENTER tuşuna basınız:

EHLO localhost

AUTH LOGIN 

<your-gmail-address-encoded-as-base64>

<yourapplication-password-generated-from-myaccounts.google.com-encoded-as-base64>

MAIL FROM: <your-gmail-address-not-encoded>

rcpt to: <destination-address-not-encoded>

DATA

Subject: System Prog. Third Week

Burada mailin gövde mesajı gönderilmektedir.

.

QUIT
