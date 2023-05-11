<?php

namespace App\Command;
use App\Entity\User;
use Symfony\Component\Console\Attribute\AsCommand;
use Symfony\Component\Console\Command\Command;
use Symfony\Component\Console\Input\InputArgument;
use Symfony\Component\Console\Input\InputInterface;
use Symfony\Component\Console\Input\InputOption;
use Symfony\Component\Console\Output\OutputInterface;
use Symfony\Component\Console\Style\SymfonyStyle;

#[AsCommand(
    name: 'BlockUserCommand',
    description: 'Add a short description for your command',
)]
class BlockUserCommand extends Command
{
    protected function configure()
    {
        $this
            ->setName('app:user:block')
            ->setDescription('Block or unblock a user')
            ->addArgument('userId', InputArgument::REQUIRED, 'The ID of the user to block or unblock')
            ->addArgument('block', InputArgument::REQUIRED, 'Whether to block or unblock the user (true/false)');
    }

    protected function execute(InputInterface $input, OutputInterface $output)
    {
        $userId = $input->getArgument('userId');
        $block = $input->getArgument('block') === 'true';

        $user = $this->entityManager->getRepository(User::class)->find($userId);

        if (!$user) {
            $output->writeln(sprintf('User with ID %d not found', $userId));
            return;
        }

        $user->setBlocked($block);

        $this->entityManager->flush();

        $output->writeln(sprintf('User %d %s', $userId, $block ? 'blocked' : 'unblocked'));
    }
}
