Mini transaction
Tables:
- user
  - id , uuid
  - phone , varchar(255)
  - password , text
  - status , varchar(10) , ACTIVE, DELETED
- rate
  - id , long
  - from_currency , varchar(10)
  - to_currency , varchar(10)
  - rate , long
- card
  - id , long
  - balance , long, Agar uzs bo’lsa tiyinda, usd bo’lsa sentda
  - name , varchar(255)
  - card_number, varchar(16), unique
  - type, varchar(10)(HUMO, VISA), start with 9860 - HUMO, 4200 - VISA
  - currency , varchar(10), HUMO - UZS, VISA - USD
  - user_id , uuid
- transaction
  - id , uuid
  - sender_card_id , long
  - receiver_card_id , long
  - sender_amount , long
  - receiver_amount , long
  - status , varchar(10) NEW, SUCCESS, ERROR
  - time , long
- User controller:
  - Register (phone, pasword)
  - Login (phone, password)
  - Delete
- Rate Controller:
  - Bazadan o’zgartriladi crud kerak emas
- Card Controller
  - Add (balance ham yoziladi add payti)
  - My cards
  - Card info (karta nomeri kiritiladi va bazadagi birorta kartaga mos kelsa info sifatida karta
malumotlari qaytariladi)
- Transaction Controller:
  - hold
  - confirm
  
  
User registratsiya , login , delete qila olsin. User Karta qo’sha olsin va o’zining kartalari ro’yxatini
chaqira olsin.

Tranzaksiya
Ixtiyoriy payment app da pul o’tkazmalari ishlashini kuzating va u yerda kartalar listini olish ,
qabul qiluvchining malumotlarini olish va tranzaksiyani amalga oshirish bor. Huddi shu ketma
ketlikda front zaproslarni jo’natadi deb tasavvur qiling. Requestlarning ketma-ketligi quyidagicha:
User o’z kartasidan shu tizimdagi boshqa bir kartaga tranzaksiya qila olishi mumkin!!!
User tranzaksiya qilmoqchi bo’lsa avval o’z kartalari listini oladi , va card info orqali ikkinchi
kartani informatsiyasini oladi va ketma ket ikkita methodga muroojat qiladi hold va confirmga.
Tranzaksiya ikki qismdan iborat hold va confirm; hold - bunda sender , receiver karta idlari va
sender amount jo’natiladi, holda hali tranzaksiya amalga oshirilmaydi , kartaning balancelari va
boshqa havfsizlik tekshiruvlari tekshirilib, NEW statusda tranzaksiya yaratiladi, frontga
tranzaksiya id si jo’natiladi, confirm da faqat tranzaksiya id si jo’natiladi va card tabledagi sender
balansidan pul kamayib receiver balansida pul ko’payadi.
Agar karta turlari xar xil bo’lsa rate tabledan kerakli malumotlarni olib shu asosida sender va
receiver amountlari hisoblanadi va shunga mos balanslar hau o’zgaradi.
HUddi shu requirementlarga javob beradigan rest api uchun Spring boot project qilish so’raladi
