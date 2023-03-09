<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Mailer\MailerInterface;
use Symfony\Component\Mime\Email;
use Symfony\Component\Routing\Annotation\Route;
use Swift_SmtpTransport;
use Swift_Message;
use Swift_Mailer;

class MailerController extends AbstractController
{

    //#[Route('/email', name: 'email')]
    public function sendEmail( Swift_Mailer $mailer)
    {

        $message = (new Swift_Message('Hello Email'))
            ->setFrom('mohamedalaeddine.naffeti@esprit.tn')
            ->setTo('mohamedalaeddine.naffeti@esprit')
            ->setBody(
                $this->render(
                    'mailer/index.html.twig',

                ),
                'text/html'
            )
            ->addPart(
                $this->renderView(
                    'mailer/index.html.twig',

                ),
                'text/plain'
            );
        $mailer->send($message);

        return $this->render('mailer\index.html.twig');


    }


}
