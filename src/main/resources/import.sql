INSERT INTO `pizzas`(`name`, `description`, `price`) VALUES('Margherita', 'Pomodoro - mozzarella', '6.00')
INSERT INTO `pizzas`(`name`, `description`, `price`) VALUES('Quattro formaggi', 'Mozzarella - fontina - emmental - gorgonzola', '8.50')
INSERT INTO `pizzas`(`name`, `description`, `price`) VALUES('Diavola', 'Pomodoro - mozzarella - salame piccante', '8.50')

INSERT INTO  `ingredients`(`name`) VALUES('pomodoro');
INSERT INTO  `ingredients`(`name`) VALUES('mozzarella');
INSERT INTO  `ingredients`(`name`) VALUES('basilico');
INSERT INTO  `ingredients`(`name`) VALUES('mozzarella di bufala');
INSERT INTO  `ingredients`(`name`) VALUES('salamino piccante');
INSERT INTO  `ingredients`(`name`) VALUES('olive');
INSERT INTO  `ingredients`(`name`) VALUES('carciofini');

INSERT INTO `users`(`email`, `first_name`, `last_name`, `password`) VALUES('gioan@email.it', 'Giovanni', 'Bassani', '{noop}gioan');
INSERT INTO `users`(`email`, `first_name`, `last_name`, `password`) VALUES('buster@email.it', 'Buster', 'Keaton','{noop}buster');

INSERT INTO `roles`(`id`, `name`) VALUES(1, 'ADMIN');
INSERT INTO `roles`(`id`, `name`) VALUES(2, 'USER');

INSERT into `users_roles`(`user_id`, `roles_id`) VALUES(1, 1);
INSERT into `users_roles`(`user_id`, `roles_id`) VALUES(2, 2);