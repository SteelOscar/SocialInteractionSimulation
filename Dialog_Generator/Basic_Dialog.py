import markovify
import os
import random

PATH = os.getcwd()
DICTIONARIES = os.path.join(PATH, 'source')
DIALOGS = os.path.join(PATH, 'dialogs')


class BasicDialog:
	
	def __init__(self):
		if not os.path.exists(DIALOGS):
			os.makedirs(DIALOGS)
		self.source = 'CommonSource.txt'
		self.out = 'CommonDialog.txt'
		self.pathIn = os.path.join(DICTIONARIES, self.source)
		self.pathOut = os.path.join(DIALOGS, self.out)
		# Приветсвие
		self.greet = ['Привет! ', 'Добрый день! ', 'Здарствуй. ', 'Здраствуйте! ', 'Привет, давно не общались! ',
		              'Здаров! ', 'Сколько лет, склько зим! ', 'Привет. ', 'Привет. Как сам? ', 'Какие люди! ',
		              'Здаров, как дела? ', 'Здрастуйте, хотел обсудить то, о чем мы сегодня говорили. ', 'Хей! ']
		# Фразы, которые могу всталвятся в начало реплики
		self.particles = ['Хм... ', 'Точно... ', 'Хотя... ', 'Если опираться на то, о чем мы говорили... ',
		                  'Если подумать...',
		                  'В этих словах есть смысл. ', 'Однако, не соглашусь с этим выражением! ',
		                  'Я бы хотел оставить эту тему на потом... ',
		                  'Однако... ']
		self.chance = 10  # Шанс прока частицы-вствки
	
	# Генерируем диалоги
	def createDialog(self, minSize=8, maxSize=20, mode='w'):
		def getOrder():  # Счетчик
			seed = 0
			while True:
				yield seed % 2
				seed += 1
		
		paste = str()
		step = getOrder()
		# Файл откуда берем словари
		with open(self.pathIn, encoding='utf-8') as file:
			text = file.read()  # Считываем содержимое файла.
			if not text:  # Смотрим, чтобы файл не был пустым
				return print(f'Словарь {self.source} пуст!')
		text_model = markovify.Text(text)  # Создаем модель цепей Макрова
		# Файл куда сохраняем диалоги
		with open(os.path.join(DIALOGS, self.out), mode, encoding='utf-8') as file:
			dialogLen = int(random.uniform(minSize, maxSize))  # Выбираем какое количетсов фраз будет в диалоге
			unique_phrase = list()  # Список уникальных фраз (чтобы не повторятся)
			for line in range(dialogLen):
				if line < 2:  # Смотрим если меньше 2 то добавляем приветсвие [greet]
					greeting = random.choice(self.greet)  # Выбираем случайное приветсвие из [greet]
				file.write(f"[{next(step) + 1}]: {greeting}")  # Красиво оформляем "[n]: ..."
				numberOfPhrase = int(random.uniform(1, 3))  # Выбираем сколько предложений будет в реплике
				greeting = str()  # Обнуляем привествие
				for sentence in range(numberOfPhrase):
					try:
						phrase = str()  # Предложение
						if (line > 2) and (random.randint(0, 100) < self.chance) and not paste:
							paste = random.choice(
								self.particles)  # Смотрим если это первое предложение, то добавляем вставку
						param = int(random.uniform(100, 400))
						while not phrase:  # Избаегаем пустых предложений
							phrase = text_model.make_short_sentence(param)  # Генерируем предложения
						for non_unique in unique_phrase:  # Смотрим, чтобы такой фразы еще небыло
							while phrase == non_unique:  # Пока фраза не станет уникальной - генерируем новые фразы
								phrase = text_model.make_short_sentence(param)
						file.write(
							f'{paste}{phrase[0].title()}{phrase[1:]} ')  # Следим, за тем, чтобы первая буква была заглавной
						unique_phrase.append(phrase)  # Если все удачно добавляем фразы в набор уникальных
					except:
						pass
					paste = str()  # Обнуляем вставку
				file.write('\n')
	
	# Генерируем посты
	def createPost(self, minSize=4, maxSize=16, mode='w'):
		# Файл откуда берем словари
		with open(self.pathIn, encoding='utf-8') as file:
			text = file.read()  # Считываем содержимое файла.
			if not text:  # Смотрим, чтобы файл не был пустым
				return print(f'Словарь {self.source} пуст!')
		text_model = markovify.Text(text)  # Создаем модель цепей Макрова
		# Файл куда сохранять пост
		with open(os.path.join(DIALOGS, self.out), mode, encoding='utf-8') as file:
			postLen = int(random.uniform(minSize, maxSize))  # Выбираем какое количетсво предложений будет в посте
			unique_phrase = list()  # Список уникальных предложений (чтобы не повторятся)
			for sentence in range(postLen):
				try:
					phrase = str()  # Предложение
					param = int(random.uniform(100, 400))
					while not phrase:  # Избаегаем пустых предложений
						phrase = text_model.make_short_sentence(param)  # Генерируем предложения
					for non_unique in unique_phrase:  # Смотрим, чтобы такого предложения еще небыло
						while phrase == non_unique:  # Пока предложение не станет уникальным - генерируем новые предложения
							phrase = text_model.make_short_sentence(param)
					file.write(
						f'{phrase[0].title()}{phrase[1:]}\n')  # Следим, за тем, чтобы первая буква была заглавной
					unique_phrase.append(phrase)  # Если все удачно добавляем фразы в набор уникальных
				except:
					pass
			file.write('\n')
