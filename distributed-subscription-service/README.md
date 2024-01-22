# Ödev -2

Java programlama diliyle Taşıma (Transport) Katmanı gönderim fonksiyonlarını kullanarak dağıtık bir abonelik sistemi geliştirmeniz beklenmektedir.

Bu abonelik sistemi soket üzerinde SMTP, HTTP vb. bir protokol yerine ödev kapsamında ortaya atılan ASUP (Abonelik Servisi Uyelik Protokolü) isminde ilkel bir protokol ile temel sözdizimi aşağıda paylaşılan sırada gerçekleşmelidir.

Yük dağılımı, hata tolerans vb. taleplerde başvurulan dağıtık mimaride her bir sunucu, aboneleri ve abonelerin sistemde çevrimiçi/çevrimdışı olduğu bilgileri tutmaktadır. İstemci bir sunucudan abone olup; bir başka sunucudan sisteme giriş yapabilmelidir.

Sunucular listenin diğer sunucularda da güncellenmesi sonrası (güncel listeyi alan diğer 2 sunucudan “55 TAMM” mesajı gelmesinden sonra) hizmet verdiği istemciye yanıt dönmelidir.

Her sunucu concurrent (eşzamanlı) istemci erişimi sırasında bünyesindeki listelere erişimi thread-safe sunmak zorundadır. (Kritik bölgelerde lock, synchronized vb. yapılar kullanılmalıdır.)


## Yapılacaklar

- [ ] Hali hazırda birbirine ping atan 3 adet sunucu (Server1.class, Server2.class, Server3.class) sunuculara istek gönderen bir istemci (Client.class) kodu paylaşılmııştır.
- [ ] Paylaşılan sunucu uygulaması, gelen her istek için yeni bir thread açmaktadır.
- [ ] Sunucuların kendilerinde bir örneğini tutacakları Aboneler.class sınıfı da paylaşılmıştır. Sunucular, istemcilerden gelen talepler doğrultusunda listeleri güncellendiğinde diğer sunuculara bu nesneyi göndererek haberdar etmelidirler.

## Kriterler

- [x] Geliştirdiğiniz dağıtık uygulamanın senaryosunun sorunsuz çalışması, yapılan hatalı isteklerde hata mesajını döndürmesi,
- [x] İstemcilerden gelen metin istekleri ile sunuculardan gelen nesne isteklerini ayırt edip, ona göre işleyebilmesi,
- [x] Sunucudan gelen nesnenin doğruluğunun kontrolü,
- [x] Sunucudan gelen listenin güncellik kontrolü,
- [x] Sunucu1 ile iletişime geçerek abone olan bir istemci, ne kadar bir zaman sonra (milisaniye) Sunucu2 üzerinden giriş yapabiliyor?
- [x] 1.sn’ de kaç adet isteğe yanıt verebiliyor?





